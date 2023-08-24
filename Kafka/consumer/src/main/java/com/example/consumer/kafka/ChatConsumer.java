package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.example.consumer.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatConsumer {
    private final ChatRepository chatRepository;

    public ChatConsumer(final ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @KafkaListener(id = "chatListener1", topics = "#{'${spring.kafka.topics.chat}'.split(',')}", containerFactory = "kafkaListenerContainerFactory")
    public void recordChat(final List<Chat> chat, final Acknowledgment ack) {
        log.info("Consumed size : {}", chat.size());
        chat.forEach(chatRepository::save);
        ack.acknowledge();
    }
}
