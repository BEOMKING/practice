package com.example.rabbitmq.presentation.room;

import com.example.rabbitmq.infra.sender.RoomSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    private  final RoomSender roomSender;

    public RoomController(RoomSender roomSender) {
        this.roomSender = roomSender;
    }

    @PostMapping("/{message}")
    public void sendRoom(@PathVariable String message) {
        roomSender.sendRoom(message);
    }
}
