package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.example.consumer.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatConsumer {
    private final ChatRepository chatRepository;

    public ChatConsumer(final ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @KafkaListener(id = "chatListener1", topics = "chat")
    public void recordChat(final Chat chat) {
        try {
            Thread.sleep(10);
            log.info("Consumed message : {}", chat.getContent());
            chatRepository.save(chat);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        log.info("Consumed message : {}", chat.size());
//        chat.forEach(chatRepository::save);
    }
}
