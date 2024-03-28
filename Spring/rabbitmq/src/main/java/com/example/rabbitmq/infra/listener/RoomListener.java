package com.example.rabbitmq.infra.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoomListener {

    @RabbitListener(queues = "room")
    public void receiveRoom(String message) {
        log.info("Received room: {}", message);
    }
}
