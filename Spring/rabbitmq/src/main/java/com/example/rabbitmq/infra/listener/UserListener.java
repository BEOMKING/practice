package com.example.rabbitmq.infra.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserListener {

    @RabbitListener(queues = "user")
    public void receiveUser(String message) {
        log.info("Received user: {}", message);
    }
}
