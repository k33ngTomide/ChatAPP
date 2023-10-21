package com.talkative.data.repositories;

import com.talkative.data.models.Chat;
import com.talkative.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Optional<Chat> findChatByChatNameContainingAndParticipantIn(String chatName, List<User> participant);
}
