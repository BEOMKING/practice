package com.example.rabbitmq.infra.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RoomSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRoom(String message) {
        rabbitTemplate.convertAndSend("request", "chat.*.room.send", message);
    }
}