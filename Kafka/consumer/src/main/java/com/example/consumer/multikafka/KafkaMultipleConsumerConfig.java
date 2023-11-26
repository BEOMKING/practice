package com.example.consumer.multikafka;

import com.example.consumer.domain.Order;
import com.example.consumer.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static java.util.Objects.nonNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaMultipleConsumerConfig {
    private final KafkaMultipleProperties kafkaMultipleProperties;

    @Bean
    @Qualifier("userKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, User>> userKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfig("user"), new StringDeserializer(), new JsonDeserializer<>(User.class)));
        factory.setCommonErrorHandler(customErrorHandler());
        return factory;
    }

    @Bean
    @Qualifier("orderKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Order>> orderKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfig("order"), new StringDeserializer(), new JsonDeserializer<>(Order.class)));
        factory.setCommonErrorHandler(customErrorHandler());
        return factory;
    }

    @Bean
    @Qualifier("exampleKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> exampleKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfig("example")));
        return factory;
    }

    private Map<String, Object> consumerConfig(final String consumerName) {
        final Map<String, Object> properties = kafkaMultipleProperties.buildCommonProperties();

        if (nonNull(kafkaMultipleProperties.getConsumers())) {
            properties.putAll(kafkaMultipleProperties.getConsumers().get(consumerName).buildProperties());
        }

        log.info("Kafka Consumer '{}' Properties: {}", consumerName, properties);
        return properties;
    }

    private DefaultErrorHandler customErrorHandler() {
        return new DefaultErrorHandler((record, exception) ->
                log.error("[Error] topic = {}, key = {}, value = {}, error message = {}", record.topic(),
                        record.key(), record.value(), exception.getMessage())
        );
    }
}
