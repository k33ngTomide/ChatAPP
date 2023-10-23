package com.talkative.dtos.request;

import com.talkative.data.models.User;
import lombok.Data;

import java.util.List;

@Data
public class FindChatRequest {

    private String firstChatName;
    private String secondChatName;
    private  List<User> participant;
}
