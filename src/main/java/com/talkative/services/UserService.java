package com.talkative.services;

import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest);

    void createChat(CreateChatRequest createChatRequest);
}
