package com.example.consumer.domain;

import lombok.Getter;

@Getter
public class Chat {
    private String id;
    private Long createdAt;
    private String content;
}
