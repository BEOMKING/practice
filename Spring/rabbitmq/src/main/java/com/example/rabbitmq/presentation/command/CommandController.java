package com.example.rabbitmq.presentation.command;

import com.example.rabbitmq.infra.sender.CommandSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandController {

    private final CommandSender commandSender;

    public CommandController(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @PostMapping("/{message}")
    public void sendCommand(@PathVariable String message) {
        commandSender.sendCommand(message);
    }
}
