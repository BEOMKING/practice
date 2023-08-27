package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "${spring.kafka.topics.chat}", brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}, ports = 9092)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatConsumerTestUsingTestConfiguration {
    @Value("${spring.kafka.topics.chat}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterAll
    void tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE chat");
    }

    @Test
    void recordChat() throws Exception {
        // given
        final Chat chat = new Chat("1", 1624368000000L, "Hello Kafka 1");

        // when
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(chat));
        Thread.sleep(3000L);

        // then
        final Integer actual = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat", Integer.class);
        assertThat(actual).isEqualTo(1);
    }

    @TestConfiguration
    static class KafkaTestConfig {
        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }

        @Bean
        public ProducerFactory<String, String> producerFactory() {
            return new DefaultKafkaProducerFactory<>(producerConfigs());
        }

        @Bean
        public Map<String, Object> producerConfigs() {
            return Map.of(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
            );
        }
    }
}
