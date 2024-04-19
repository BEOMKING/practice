package com.example.rabbitmq.presentation.user;

import com.example.rabbitmq.infra.sender.UserSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSender userSender;

    public UserController(UserSender userSender) {
        this.userSender = userSender;
    }

    @PostMapping("/{message}")
    public void sendUser(@PathVariable String message) {
        userSender.sendUser(message);
    }
}
