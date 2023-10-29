package com.talkative.utils;

import com.talkative.data.models.User;
import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.request.SendMessageRequest;
import com.talkative.dtos.response.RegisterUserResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {


    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setEmail(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        return user;
    }

    public static RegisterUserResponse map (User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getEmail());
        registerUserResponse.setRegisterDate(DateTimeFormatter
                .ofPattern("EEEE dd/MMM/yyyy HH:mm:ss a")
                .format(LocalDateTime.now()));

        return registerUserResponse;
    }

    public static CreateChatRequest map(SendMessageRequest sendMessageRequest) {
        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser(sendMessageRequest.getFrom());
        createChatRequest.setSecondUser(sendMessageRequest.getTo());
        return createChatRequest;
    }


}
