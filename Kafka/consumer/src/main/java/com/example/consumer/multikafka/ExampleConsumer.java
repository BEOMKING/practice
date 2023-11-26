package com.example.consumer.multikafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExampleConsumer {
    @KafkaListener(
            topics = "${kafka.consumers.example.topic}",
            groupId = "${kafka.consumers.example.group-id}",
            containerFactory = "exampleKafkaListenerContainerFactory"
    )
    public void consume(final String message) {
        log.info("ExampleConsumer \nMessage = {}", message);
    }
}
