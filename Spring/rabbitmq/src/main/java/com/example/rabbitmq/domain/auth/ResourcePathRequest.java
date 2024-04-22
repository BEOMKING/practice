package com.example.rabbitmq.domain.auth;

public record ResourcePathRequest(String username, String vhost, String resource, String name, String permission) {
}
