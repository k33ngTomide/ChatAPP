package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.dtos.request.SendMessageRequest;

public interface MessageService {

    void sendMessage(SendMessageRequest sendMessageRequest, Chat chat);
}
