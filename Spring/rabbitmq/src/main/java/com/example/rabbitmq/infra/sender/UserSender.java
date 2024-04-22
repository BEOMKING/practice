package com.example.rabbitmq.infra.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUser(String message) {
        rabbitTemplate.convertAndSend("request", "chat.*.user.send", message);
    }
}