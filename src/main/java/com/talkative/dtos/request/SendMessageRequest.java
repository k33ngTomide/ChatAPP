package com.talkative.dtos.request;

import lombok.Data;

import java.time.LocalDate;


@Data
public class SendMessageRequest {

    private String from;
    private String to;
    private String messageBody;
    private LocalDate dateSent = LocalDate.now();
    private LocalDate timeSent = LocalDate.now();
}
