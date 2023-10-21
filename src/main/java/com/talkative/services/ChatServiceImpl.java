package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.repositories.ChatRepository;
import com.talkative.dtos.request.FindChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;


    @Override
    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public Chat findChat(FindChatRequest findChatRequest) {
        Optional<Chat> chat = chatRepository
                .findChatByChatNameContainingAndParticipantIn(findChatRequest.getChatName(), findChatRequest.getParticipant());
        return chat.orElse(null);
    }


}
