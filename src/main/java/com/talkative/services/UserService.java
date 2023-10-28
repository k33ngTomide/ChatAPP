package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.Message;
import com.talkative.data.models.User;
import com.talkative.dtos.request.*;
import com.talkative.dtos.response.RegisterUserResponse;

import java.util.List;

public interface UserService {

    RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest);

    Chat createChat(CreateChatRequest createChatRequest);

    User findByEmail(String username);

    void sendMessage(SendMessageRequest sendMessageRequest);

    void deleteMessage(DeleteMessageRequest deleteMessage);

    List<String> viewAllMessage(String firstUser, String secondUser);

    void deleteChat(DeleteChatRequest deleteChatRequest);
}
