package com.talkative.dtos.request;

import lombok.Data;
import lombok.NonNull;
import net.bytebuddy.utility.nullability.NeverNull;

@Data
public class RegisterUserRequest {

    private String username;
    private String password;
}
