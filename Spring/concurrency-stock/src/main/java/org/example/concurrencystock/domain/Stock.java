package org.example.concurrencystock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;

@Getter
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long quantity;

    @Version
    private Long version;

    public Stock() {
    }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(final Long quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        this.quantity -= quantity;
    }
}
