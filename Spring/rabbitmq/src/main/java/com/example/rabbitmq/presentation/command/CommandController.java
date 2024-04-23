package com.example.rabbitmq.presentation.command;

import com.example.rabbitmq.domain.chat.Chat;
import com.example.rabbitmq.domain.command.Command;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandController {

    private final RabbitTemplate rabbitTemplate;

    public CommandController(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/{type}")
    public Object sendCommand(@PathVariable String type, @RequestBody CommandRequest request) {
        final String message = request.command();
        if (message.startsWith("/")) {
            final String[] commandAndArgs = message.substring(1).split("\\s", 2);
            final Command command = new Command(message, commandAndArgs[0], commandAndArgs[1].split("\\s"));

            return rabbitTemplate.convertSendAndReceive(
                    "request",
                    "command." + commandAndArgs[0],
                    command);
        }

        if (type.equals("chat")) {
            Chat chat = new Chat(type, "admin");
            rabbitTemplate.convertAndSend(request.exchange(), request.routingKey(), chat);

            return "send Chat Message";
        }

        rabbitTemplate.convertAndSend(request.exchange(), request.routingKey(), type);
        return "send Message";
    }
}
