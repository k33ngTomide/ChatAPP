package com.talkative.dtos.request;

import lombok.Data;

@Data
public class FindMessageRequest {

    private String chatId;
    private String messageBody;
}
