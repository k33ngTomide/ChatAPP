package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.User;
import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.request.SendMessageRequest;
import com.talkative.dtos.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest);

    Chat createChat(CreateChatRequest createChatRequest);

    User findByEmail(String username);

    void sendMessage(SendMessageRequest sendMessageRequest);
}
