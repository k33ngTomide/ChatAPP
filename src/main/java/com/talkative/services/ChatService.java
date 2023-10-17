package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.dtos.request.CreateChatRequest;

public interface ChatService {
    void createChat(Chat chat);

}
