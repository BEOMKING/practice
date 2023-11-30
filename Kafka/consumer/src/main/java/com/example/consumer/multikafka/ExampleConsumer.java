package com.example.consumer.multikafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExampleConsumer {
    @KafkaListener(
            topics = "${kafka.consumers.example.topic}",
            containerFactory = "exampleKafkaListenerContainerFactory"
    )
    public void consume(final String message, final ConsumerRecordMetadata metadata) {
        log.info("Offset : {}\n Message : {}", metadata.offset(), message);
    }
}
