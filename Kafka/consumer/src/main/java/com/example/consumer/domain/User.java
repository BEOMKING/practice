package com.example.consumer.domain;

import lombok.Getter;

@Getter
public class User {
    private String id;
    private String name;
    private String email;

    public User() {
    }

    public User(final String id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
