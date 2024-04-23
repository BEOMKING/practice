package com.example.rabbitmq.domain.command;

public record Command(String body, String command, String[] arguments) {
}
