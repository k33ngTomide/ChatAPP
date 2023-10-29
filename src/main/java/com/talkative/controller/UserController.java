package com.talkative.controller;

import com.talkative.dtos.request.DeleteChatRequest;
import com.talkative.dtos.request.DeleteMessageRequest;
import com.talkative.dtos.request.RegisterUserRequest;
import com.talkative.dtos.request.SendMessageRequest;
import com.talkative.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xTalka")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            userService.registerWith(registerUserRequest);
            return "User Registered Successfully";
        } catch (Exception error){
            return error.getMessage();
        }

    }

    @PostMapping("/sendMessage")
    public String message(SendMessageRequest sendMessageRequest){
        try {
            userService.sendMessage(sendMessageRequest);
            return "Message Sent Successfully";
        } catch (Exception exception){
            return exception.getMessage();
        }
    }


    @GetMapping("/viewChat/{firstUser}/{secondUser}")
    public List<String> viewChat(@PathVariable String firstUser, @PathVariable String secondUser){
        try{
            return userService.viewAllMessage(firstUser, secondUser);
        } catch (Exception exception){
            return List.of(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteMessage")
    public String deleteMessage(@RequestBody DeleteMessageRequest deleteMessageRequest){
        try{
            userService.deleteMessage(deleteMessageRequest);
            return "Message Deleted Successfully";
        } catch (Exception exception){
            return exception.getMessage();
        }
    }

    @DeleteMapping("/deleteChat")
    public String deleteChat(@RequestBody DeleteChatRequest deleteChatRequest){
        try{
            userService.deleteChat(deleteChatRequest);
            return "Chat deleted Successfully";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }

}
