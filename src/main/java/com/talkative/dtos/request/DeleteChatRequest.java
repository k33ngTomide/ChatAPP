package com.talkative.dtos.request;

import lombok.Data;

@Data
public class DeleteChatRequest {

    private String from;
    private String to;
}
