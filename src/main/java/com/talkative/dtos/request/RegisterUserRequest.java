package com.talkative.dtos.request;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;
    private String password;
}
