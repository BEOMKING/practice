package com.example.consumer.multikafka;

import com.example.consumer.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConsumer {
    @KafkaListener(
            topics = "${kafka.consumers.user.topic}",
            groupId = "${kafka.consumers.user.group-id}",
            containerFactory = "userKafkaListenerContainerFactory"
    )
    public void consume(final User user) {
        log.info("UserConsumer \nId = {}\nName = {}\nEmail = {}", user.getId(), user.getName(), user.getEmail());
    }
}
