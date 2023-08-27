package com.example.consumer.domain;

import lombok.Getter;

@Getter
public class Chat {
    private String id;
    private Long createdAt;
    private String content;

    public Chat() {
    }

    public Chat(final String id, final Long createdAt, final String content) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
    }
}
