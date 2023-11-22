package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "${spring.kafka.topics.chat}", brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}, ports = 9092)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatConsumerTest {
    @Value("${spring.kafka.topics.chat}")
    private String topic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterAll
    void tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE chat");
    }

    @Test
    void recordChat() throws Exception {
        // given
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send(new ProducerRecord<>(topic, objectMapper.writeValueAsString(new Chat(String.valueOf(i), 1624368000000L, "Hello Kafka " + i))));
        }

        // when
        Thread.sleep(3000L);

        // then
        final Integer actual = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat", Integer.class);
        assertThat(actual).isEqualTo(100);
    }
}