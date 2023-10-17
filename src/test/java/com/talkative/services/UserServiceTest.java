package com.talkative.services;

import com.talkative.data.models.User;
import com.talkative.data.repositories.ChatRepository;
import com.talkative.data.repositories.UserRepository;
import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.response.RegisterUserResponse;
import com.talkative.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @BeforeEach
    public void deleteBeforeEveryTest(){
        userRepository.deleteAll();
        chatRepository.deleteAll();
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
        User user = new User();
        user.setEmail("Tomide");
        user.setPassword("password");

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername(user.getEmail());
        registerUserRequest.setPassword(user.getPassword());
        userService.registerWith(registerUserRequest);

        User user1 = new User();
        user1.setEmail("Akintomide");
        user1.setPassword("password");

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername(user1.getEmail());
        registerUserRequest1.setPassword(user1.getPassword());
        userService.registerWith(registerUserRequest1);

        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser(user);
        createChatRequest.setSecondUser(user1);

        userService.createChat(createChatRequest);
        assertThat(chatRepository.count(), is(1L));

    }

}