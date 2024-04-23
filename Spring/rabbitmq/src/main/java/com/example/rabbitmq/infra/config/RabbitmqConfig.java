package com.example.rabbitmq.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String REQUEST_EXCHANGE = "request";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Bean
	public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(REQUEST_EXCHANGE);
    }

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange("chat");
    }

    @Bean
    public FanoutExchange myUserExchange() {
        return new FanoutExchange("user." + username);
    }

    @Bean
    public FanoutExchange roomExchange() {
        return new FanoutExchange("room");
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
    public Queue deadLetterQueue() {
        return new Queue("dead-letter");
    }

    @Bean
    public Queue myUserQueue() {
        return QueueBuilder.durable("user." + username)
                .deadLetterExchange("")
                .deadLetterRoutingKey("dead-letter").build();
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

    @Bean
    public Binding bindingMyUserQueue(TopicExchange topicExchange, FanoutExchange myUserExchange) {
        return BindingBuilder.bind(myUserExchange).to(topicExchange).with("*.user." + username);
    }

    @Bean
    public Binding bindingChatExchange(TopicExchange chatExchange, FanoutExchange roomExchange) {
        return BindingBuilder.bind(roomExchange).to(chatExchange).with("*.room.#");
    }
}
