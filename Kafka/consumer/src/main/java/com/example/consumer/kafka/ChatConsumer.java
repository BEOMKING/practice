package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.example.consumer.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ChatConsumer {
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    public ChatConsumer(final ChatRepository chatRepository, final ObjectMapper objectMapper) {
        this.chatRepository = chatRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat")
    public void recordChat(final String message) throws JsonProcessingException {
        log.info("Consumed message : {}", message);
        final Chat chat = objectMapper.readValue(message, Chat.class);
        chatRepository.save(chat);
    }
}
