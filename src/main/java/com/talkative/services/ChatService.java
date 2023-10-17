package com.talkative.services;

import com.talkative.dtos.request.CreateChatRequest;

public interface ChatService {
    void createChat(CreateChatRequest createChatRequest);

}
