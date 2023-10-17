package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.User;
import com.talkative.data.repositories.UserRepository;
import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.response.RegisterUserResponse;
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

    @Override
    public RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest) {
        findUser(registerUserRequest);
        return map(userRepository.save(map(registerUserRequest)));
    }

    @Override
    public void createChat(CreateChatRequest createChatRequest) {
        Chat chat = new Chat();
        chat.setChatName(createChatRequest.getFirstUser() + " "
                + createChatRequest.getSecondUser());
        chat.getParticipant().addAll(List
                .of(findByEmail(createChatRequest.getFirstUser()), findByEmail(createChatRequest.getSecondUser())));
        chatService.createChat(chat);
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




}
