package com.talkative.dtos.request;

import com.talkative.data.models.User;
import lombok.Data;

@Data
public class CreateChatRequest {

    private String firstUser;
    private String secondUser;
}
