package com.talkative.services;

import com.talkative.data.models.Chat;
import com.talkative.data.repositories.ChatRepository;
import com.talkative.dtos.request.CreateChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.talkative.utils.Mapper.map;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void createChat(CreateChatRequest createChatRequest) {
        Chat chat = new Chat();
        map(createChatRequest, chat);
        chatRepository.save(chat);
    }


}
