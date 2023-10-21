package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.Message;
import com.talkative.data.repositories.MessageRepository;
import com.talkative.dtos.request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        Message message = new Message();
        message.setBody(sendMessageRequest.getMessageBody());
        message.setChatId(chat.getId());
        message.setRead(false);
        message.setDateCreated(sendMessageRequest.getDateSent());
        messageRepository.save(message);
    }
}
