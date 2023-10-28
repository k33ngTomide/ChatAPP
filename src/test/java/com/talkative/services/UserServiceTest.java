package com.talkative.services;

import com.talkative.data.models.Message;
import com.talkative.data.repositories.ChatRepository;
import com.talkative.data.repositories.MessageRepository;
import com.talkative.data.repositories.UserRepository;
import com.talkative.dtos.request.*;
import com.talkative.exceptions.ChatNotFoundException;
import com.talkative.exceptions.MessageNotFoundException;
import com.talkative.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void deleteBeforeEveryTest(){
        userRepository.deleteAll();
        chatRepository.deleteAll();
        messageRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Username");
        registerUserRequest.setPassword("password");

        userService.registerWith(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

    }

    @Test
    public void testThatUserIsUnique(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Username");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest_1 = new RegisterUserRequest();
        registerUserRequest_1.setUsername("Username");
        registerUserRequest_1.setPassword("password");
        assertThrows(UserAlreadyExistException.class,
                () -> userService.registerWith(registerUserRequest_1));

    }

    @Test
    public void testThatUserCanCreateChat(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);



        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser("Tomide");
        createChatRequest.setSecondUser("Akintomide");

        userService.createChat(createChatRequest);
        assertThat(chatRepository.count(), is(1L));

    }

    @Test
    public void testThatChatIsCreatedWhenUserSendsFirstMessage(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser("Tomide");
        createChatRequest.setSecondUser("Akintomide");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Tomide");
        sendMessageRequest.setTo("Akintomide");
        sendMessageRequest.setMessageBody("There is place i want you to be");

        userService.sendMessage(sendMessageRequest);
        assertThat(chatRepository.count(), is(1L));

    }

    @Test
    public void testThatNoNewChatIsNot_createdIfMessageIsNotTheFirst(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser("Tomide");
        createChatRequest.setSecondUser("Akintomide");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want you to be");
        userService.sendMessage((sendMessageRequest));
        assertThat(chatRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Tomide");
        sendMessageRequest1.setTo("Akintomide");
        sendMessageRequest1.setMessageBody("Where?");
        userService.sendMessage(sendMessageRequest1);
        assertThat(chatRepository.count(), is(1L));

    }

    @Test
    public void testThatMessagesReplyDoesNotCreateANewChat_andMessagesAreSaved(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("Muiliyu");
        registerUserRequest2.setPassword("password");
        userService.registerWith(registerUserRequest2);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want you to be");
        userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Tomide");
        sendMessageRequest1.setTo("Akintomide");
        sendMessageRequest1.setMessageBody("Where?");
        userService.sendMessage(sendMessageRequest1);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(2L));

        SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
        sendMessageRequest2.setFrom("Muiliyu");
        sendMessageRequest2.setTo("Tomide");
        sendMessageRequest2.setMessageBody("Senior Man, How far my guy?");
        userService.sendMessage(sendMessageRequest2);

        assertThat(chatRepository.count(), is(2L));
        assertThat(messageRepository.count(), is(3L));

        SendMessageRequest sendMessageRequest3 = new SendMessageRequest();
        sendMessageRequest3.setFrom("Tomide");
        sendMessageRequest3.setTo("Muiliyu");
        sendMessageRequest3.setMessageBody("My oga, i dey alright o");
        userService.sendMessage(sendMessageRequest3);

        assertThat(chatRepository.count(), is(2L));
        assertThat(messageRepository.count(), is(4L));
    }

    @Test
    public void testThatUserCanDeleteAMessage(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest);


        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest_1 = new SendMessageRequest();
        sendMessageRequest_1.setFrom("Akintomide");
        sendMessageRequest_1.setTo("Tomide");
        sendMessageRequest_1.setMessageBody("Even the best is yet to show up");
        userService.sendMessage(sendMessageRequest_1);

        assertThat(messageRepository.count(), is(2L));

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setFrom("Akintomide");
        deleteMessageRequest.setTo("Tomide");
        deleteMessageRequest.setMessageBody("There is a place i want to be");
        userService.deleteMessage(deleteMessageRequest);

        assertThat(messageRepository.count(), is(1L));

    }

    @Test
    public void testThatOnlyMessageParticularToUserIsDeleted_evenIfMessageIsDuplicateInChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("Muiliyu");
        registerUserRequest2.setPassword("password");
        userService.registerWith(registerUserRequest2);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest);


        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest_1 = new SendMessageRequest();
        sendMessageRequest_1.setFrom("Akintomide");
        sendMessageRequest_1.setTo("Muiliyu");
        sendMessageRequest_1.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest_1);

        assertThat(messageRepository.count(), is(2L));

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setFrom("Akintomide");
        deleteMessageRequest.setTo("Muiliyu");
        deleteMessageRequest.setMessageBody("There is a place i want to be");
        userService.deleteMessage(deleteMessageRequest);

        assertThat(messageRepository.count(), is(1L));
    }

    @Test
    public void testThatAnExceptionIsThrownWhenUserDeleteAMessageThatNeverExisted(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest);

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setFrom("Akintomide");
        deleteMessageRequest.setTo("Tomide");
        deleteMessageRequest.setMessageBody("There is aplace iwant to be");

        assertThrows(MessageNotFoundException.class, () -> userService.deleteMessage(deleteMessageRequest));
    }

    @Test
    public void testThatAnExceptionIsThrownIfUserDeleteMessage_butChatDoesNotExist_(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setFrom("Akintomide");
        deleteMessageRequest.setTo("Tomide");
        deleteMessageRequest.setMessageBody("There is a place i want to be");

        assertThrows(ChatNotFoundException.class, () -> userService.deleteMessage(deleteMessageRequest));
    }

    @Test
    public void testThatUserCanViewChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest);

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Akintomide");
        sendMessageRequest1.setTo("Tomide");
        sendMessageRequest1.setMessageBody("The End can Also be the Beginning");
        userService.sendMessage(sendMessageRequest1);

        List<String> messages = userService.viewAllMessage("Akintomide", "Tomide");

    }

    @Test
    public void testThatUserCanDeleteChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Tomide");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Akintomide");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Akintomide");
        sendMessageRequest.setTo("Tomide");
        sendMessageRequest.setMessageBody("There is a place i want to be");
        userService.sendMessage(sendMessageRequest);

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Akintomide");
        sendMessageRequest1.setTo("Tomide");
        sendMessageRequest1.setMessageBody("The End can Also be the Beginning");
        userService.sendMessage(sendMessageRequest1);
        assertThat(chatRepository.count(), is(1L));

        DeleteChatRequest deleteChatRequest = new DeleteChatRequest();
        deleteChatRequest.setFrom("Akintomide");
        deleteChatRequest.setTo("Tomide");

        userService.deleteChat(deleteChatRequest);
        assertThat(chatRepository.count(), is(0L));


    }


}