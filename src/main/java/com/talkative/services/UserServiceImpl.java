package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.Message;
import com.talkative.data.models.User;
import com.talkative.data.repositories.UserRepository;
import com.talkative.dtos.request.*;
import com.talkative.dtos.response.RegisterUserResponse;
import com.talkative.exceptions.ChatNotFoundException;
import com.talkative.exceptions.UserAlreadyExistException;
import com.talkative.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.talkative.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Override
    public RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest) {
        validateRequestIsNotEmpty(registerUserRequest);
        findUser(registerUserRequest);
        return map(userRepository.save(map(registerUserRequest)));
    }

    private static void validateRequestIsNotEmpty(RegisterUserRequest registerUserRequest) {
        boolean userNameIsNull = registerUserRequest.getUsername() == null;
        boolean passwordIsNull = registerUserRequest.getPassword() == null;
        if ( userNameIsNull || passwordIsNull){
            throw new UserAlreadyExistException("Username or password is Invalid");
        }
    }

    @Override
    public Chat createChat(CreateChatRequest createChatRequest) {
        Chat chat = new Chat();
        chat.setChatName(createChatRequest.getFirstUser() + " "
                + createChatRequest.getSecondUser());
        chat.getParticipant().addAll(List
                .of(findByEmail(createChatRequest.getFirstUser()), findByEmail(createChatRequest.getSecondUser())));
        chatService.createChat(chat);
        return chat;
    }

    private void findUser(RegisterUserRequest registerUserRequest) {
        Optional<User> user = userRepository
                .findByEmail(registerUserRequest.getUsername());
        if(user.isPresent())
            throw new UserAlreadyExistException("Username Already Exist");

    }

    public User findByEmail(String username) {
        Optional<User> foundUser = userRepository.findByEmail(username);
        if(foundUser.isPresent())
            return foundUser.get();
        throw new UserNotFoundException("User Not Found");
    }

    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest) {
        CreateChatRequest createChatRequest = map(sendMessageRequest);
        Chat chat = findChatWithUsers(sendMessageRequest.getFrom(), sendMessageRequest.getTo());

        if (chat == null) chat = createChat(createChatRequest);
        postMessage(sendMessageRequest, chat);
    }


    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessage) {
        FindMessageRequest findMessageRequest = new FindMessageRequest();
        Chat chat = findChatWithUsers(deleteMessage.getFrom(), deleteMessage.getTo());

        validateChat(chat);

        findMessageRequest.setChatId(chat.getId());
        findMessageRequest.setMessageBody(deleteMessage.getMessageBody());
        Message message = messageService.findMessage(findMessageRequest);
        messageService.delete(message);

    }

    private static void validateChat(Chat chat) {
        if (chat == null) throw new ChatNotFoundException("Chat not Found");
    }

    @Override
    public List<String> viewAllMessage(String firstUser, String secondUser) {
        Chat foundChat = findChatWithUsers(firstUser, secondUser);
        validateChat(foundChat);

        return messageService.findAllMessages(foundChat);
    }

    @Override
    public void deleteChat(DeleteChatRequest deleteChatRequest) {
        Chat chat = findChatWithUsers(deleteChatRequest.getFrom(), deleteChatRequest.getTo());
        validateChat(chat);
        chatService.delete(chat);


    }

    private Chat findChatWithUsers(String firstUser, String secondUser) {
        FindChatRequest findChatRequest = new FindChatRequest();

        findChatRequest.setFirstChatName(firstUser + " " + secondUser);
        findChatRequest.setSecondChatName(secondUser + " " + firstUser);
        findChatRequest.setParticipant(List
                .of(findByEmail(firstUser), findByEmail(secondUser)));
        Chat chat = findChat(findChatRequest);
        return chat;
    }


    private void postMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        messageService.sendMessage(sendMessageRequest, chat);

    }

    private Chat findChat(FindChatRequest findChatRequest) {
       return chatService.findChat(findChatRequest);
    }

}
