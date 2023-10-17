package com.talkative.dtos.request;

import com.talkative.data.models.User;
import lombok.Data;

@Data
public class CreateChatRequest {

    private User firstUser;
    private User secondUser;
}
