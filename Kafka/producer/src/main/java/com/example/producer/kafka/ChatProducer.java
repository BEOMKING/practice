package com.example.producer.kafka;

import com.example.producer.domain.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ChatProducer {
    private final KafkaTemplate<String, String> template;
    private final ObjectMapper objectMapper;

    public ChatProducer(final KafkaTemplate<String, String> template, final ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    public void sendChat(final Chat chat) throws JsonProcessingException {
        final ProducerRecord<String, String> record = new ProducerRecord<>("chat", convertJson(chat));
        final CompletableFuture<SendResult<String, String>> future = template.send(record);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", chat, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to : {}", chat, ex.getMessage());
            }
        });

        // spring kafka 3.0 이하 버전에서는 아래와 같이 작성해야 함
//        final ListenableFuture<SendResult<String, String>> future = template.send(record);
//        future.addCallback(
//                result -> log.info("Sent message=[{}] with offset=[{}]", chat, result.getRecordMetadata().offset()),
//                ex -> log.error("Unable to send message=[{}] due to : {}", chat, ex.getMessage())
//        );
    }

    private String convertJson(final Chat chat) throws JsonProcessingException {
        return objectMapper.writeValueAsString(chat);
    }
}
