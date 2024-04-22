package com.example.rabbitmq.domain.auth;

public record TopicPathRequest(String username, String vhost, String resource, String name, String permission, String routingKey) {
}
