package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.dtos.request.CreateChatRequest;
import com.talkative.dtos.request.FindChatRequest;

public interface ChatService {
    void createChat(Chat chat);

    Chat findChat(FindChatRequest findChatRequest);
}
