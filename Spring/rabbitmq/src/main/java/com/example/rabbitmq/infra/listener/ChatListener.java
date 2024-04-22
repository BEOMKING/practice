package com.example.rabbitmq.infra.listener;

import com.example.rabbitmq.domain.chat.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatListener {

    @RabbitListener(queues = "chat")
    public void receiveChat(final Chat chat) {
        log.info("Received chat: {}", chat);
    }
}
