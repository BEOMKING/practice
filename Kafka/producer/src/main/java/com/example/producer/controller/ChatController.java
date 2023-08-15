package com.example.producer.controller;

import com.example.producer.domain.Chat;
import com.example.producer.dto.ChatDto;
import com.example.producer.kafka.ChatProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ChatController {
    private final ChatProducer chatProducer;

    public ChatController(final ChatProducer chatProducer) {
        this.chatProducer = chatProducer;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> sendChat(@RequestBody ChatDto chatDto) throws JsonProcessingException {
        chatProducer.sendChat(new Chat(UUID.randomUUID().toString(), System.currentTimeMillis(), chatDto.getContent()));
        return ResponseEntity.ok().build();
    }
}
