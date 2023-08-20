package com.example.producer.kafka;

import com.example.producer.domain.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ChatProducerTest {
    @Autowired
    private ChatProducer chatProducer;

    @Test
    void send1000Chat() throws JsonProcessingException {
        for (int i = 0; i < 1000; i++) {
            chatProducer.sendChat(new Chat(UUID.randomUUID().toString(), System.currentTimeMillis(), "Hello Kafka " + i));
        }
    }

}