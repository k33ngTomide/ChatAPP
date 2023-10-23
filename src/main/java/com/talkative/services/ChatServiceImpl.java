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
                .findChatByChatNameAndParticipantContains(
                        findChatRequest.getFirstChatName(),
                        findChatRequest.getParticipant());

        if(chat.isPresent()) return chat.get();
        Optional<Chat> theSameChat = checkRepositoryAgain(findChatRequest);
        return theSameChat.orElse(null);

    }

    private Optional<Chat> checkRepositoryAgain(FindChatRequest findChatRequest) {
        return chatRepository
                .findChatByChatNameAndParticipantContains(
                        findChatRequest.getSecondChatName(),
                        findChatRequest.getParticipant());
    }



}
