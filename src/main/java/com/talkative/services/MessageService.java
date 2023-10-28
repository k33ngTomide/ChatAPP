package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.Message;
import com.talkative.dtos.request.FindMessageRequest;
import com.talkative.dtos.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    void sendMessage(SendMessageRequest sendMessageRequest, Chat chat);

    Message findMessage(FindMessageRequest findMessageRequest);

    void delete(Message message);

    List<String> findAllMessages(Chat chat);
}
