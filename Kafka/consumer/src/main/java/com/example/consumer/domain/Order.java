package com.example.consumer.domain;

import lombok.Getter;

@Getter
public class Order {
    private String id;
    private String userId;
    private String productId;

    public Order() {
    }

    public Order(final String id, final String userId, final String productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }
}
