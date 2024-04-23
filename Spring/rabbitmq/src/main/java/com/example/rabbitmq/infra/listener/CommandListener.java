package com.example.rabbitmq.infra.listener;

import com.example.rabbitmq.domain.command.Command;
import com.example.rabbitmq.infra.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandListener {

    private final RabbitAdmin rabbitAdmin;
    private final RabbitmqConfig rabbitConfig;

    public CommandListener(final RabbitAdmin rabbitAdmin, final RabbitmqConfig rabbitConfig) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitConfig = rabbitConfig;
    }

    @RabbitListener(queues = "command")
    public void receiveCommand(Command request) {
        final String command = request.command();

        if (command.equals("create")) {
            final String roomName = request.arguments()[0];
            final FanoutExchange targetExchange = ExchangeBuilder.fanoutExchange("room." + roomName).build();
            rabbitAdmin.declareExchange(targetExchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(targetExchange).to(rabbitConfig.roomExchange()));
        } else if (command.equals("invite")) {
            final String roomName = request.arguments()[0];
            final String userId = request.arguments()[1];

            final FanoutExchange targetExchange = ExchangeBuilder.fanoutExchange("room." + roomName).build();
            rabbitAdmin.declareExchange(targetExchange);

            final FanoutExchange targetUserExchange = ExchangeBuilder.fanoutExchange("user." + userId).build();
            rabbitAdmin.declareExchange(targetUserExchange);


            final Queue targetUserQueue = QueueBuilder.durable("user." + userId).build();
            rabbitAdmin.declareQueue(targetUserQueue);

            rabbitAdmin.declareExchange(rabbitConfig.myUserExchange());
            rabbitAdmin.declareBinding(BindingBuilder.bind(targetExchange).to(rabbitConfig.myUserExchange()));
            rabbitAdmin.declareBinding(BindingBuilder.bind(targetUserQueue).to(targetUserExchange));
        }
    }
}
