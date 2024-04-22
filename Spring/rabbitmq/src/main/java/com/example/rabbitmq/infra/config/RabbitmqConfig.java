package com.example.rabbitmq.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String REQUEST_EXCHANGE = "request";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(REQUEST_EXCHANGE);
    }

    @Bean
    public Queue commandQueue() {
        return new Queue("command");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    @Bean
    public Queue roomQueue() {
        return new Queue("room");
    }

    @Bean
    public Binding bindingCommandQueue(Queue commandQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(commandQueue).to(topicExchange).with("command.#");
    }

    @Bean
    public Binding bindingChatUserQueue(Queue userQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(userQueue).to(topicExchange).with("chat.*.user.#");
    }

    @Bean
    public Binding bindingChatRoomQueue(Queue roomQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(roomQueue).to(topicExchange).with("chat.*.room.#");
    }
}
