package com.talkative.data.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Chats")
public class Chat {

    @Id
    private String id;
    @CreatedDate
    private LocalDate dateCreated;
    private String message;
    private String chatName;
//    @DBRef
    private List<User> users = new ArrayList<>();

}
