package com.example.rabbitmq.infra.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CommandSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCommand(String message) {
        rabbitTemplate.convertAndSend("request", "command.send", message);
    }
}