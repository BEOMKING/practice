package com.example.rabbitmq.domain.auth;

public record VhostPathRequest(String username, String vhost, String ip) {
}
