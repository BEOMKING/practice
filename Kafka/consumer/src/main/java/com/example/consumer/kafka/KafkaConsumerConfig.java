package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, Chat> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(producerConfigs(), new StringDeserializer(), new JsonDeserializer<>(Chat.class));
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG, "chatGroupId"
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Chat> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Chat> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(5);

        return factory;
    }
}
