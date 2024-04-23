package com.example.rabbitmq.presentation.command;

public record CommandRequest(String exchange, String routingKey, String command) {
}
