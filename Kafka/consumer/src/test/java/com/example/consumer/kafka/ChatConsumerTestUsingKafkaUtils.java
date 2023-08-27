package com.example.consumer.kafka;

import com.example.consumer.domain.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "${spring.kafka.topics.chat}", brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}, ports = 9092)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatConsumerTestUsingKafkaUtils {
    @Value("${spring.kafka.topics.chat}")
    private String topic;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;

    private Producer<String, String> producer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() {
        producer = new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafkaBroker), new StringSerializer(), new StringSerializer()).createProducer();
    }

    @AfterAll
    void tearDown() {
        producer.close();
        jdbcTemplate.execute("TRUNCATE TABLE chat");
    }

    @Test
    void recordChat() throws Exception {
        // given
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>(topic, objectMapper.writeValueAsString(new Chat(String.valueOf(i), 1624368000000L, "Hello Kafka " + i))));
        }

        // when
        Thread.sleep(3000L);

        // then
        final Integer actual = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat", Integer.class);
        assertThat(actual).isEqualTo(100);
    }
}