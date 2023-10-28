package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.models.Message;
import com.talkative.data.repositories.MessageRepository;
import com.talkative.dtos.request.FindMessageRequest;
import com.talkative.dtos.request.SendMessageRequest;
import com.talkative.exceptions.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Message findMessage(FindMessageRequest findMessageRequest) {
        Optional<Message> foundMessage = messageRepository.findMessageByChatIdAndBody(findMessageRequest.getChatId(),
                findMessageRequest.getMessageBody());
        if (foundMessage.isPresent()) return foundMessage.get();
        throw new MessageNotFoundException("Message Not Found");
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public List<String> findAllMessages(Chat chat) {
        List<String> messages = messageRepository.findMessagesByChatId(chat.getId());
        return messages;
    }

}
