package com.example.consumer.multikafka;

import com.example.consumer.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {
    @KafkaListener(
            topics = "${kafka.consumers.order.topic}",
            groupId = "${kafka.consumers.order.group-id}",
            containerFactory = "orderKafkaListenerContainerFactory"
    )
    public void consume(final Order order) {
        log.info("OrderConsumer \nId = {}\nUserId = {}\nProductId = {}", order.getId(), order.getUserId(), order.getProductId());
    }
}
