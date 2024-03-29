package com.example.consumer.multikafka;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.CommonClientConfigs;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaMultipleProperties {
    private List<String> bootstrapServers = new ArrayList<>(Collections.singletonList("localhost:9092"));
    private Map<String, KafkaProperties.Producer> producers;
    private Map<String, KafkaProperties.Consumer> consumers;
    private KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
    private KafkaProperties.Security security = new KafkaProperties.Security();

    public Map<String, Object> buildCommonProperties() {
        final Map<String, Object> properties = new HashMap<>();

        if (this.bootstrapServers != null) {
            properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        }

        properties.putAll(this.ssl.buildProperties());
        properties.putAll(this.security.buildProperties());

        return properties;
    }
}
