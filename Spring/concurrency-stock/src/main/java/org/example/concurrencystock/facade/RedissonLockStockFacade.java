package org.example.concurrencystock.facade;

import org.example.concurrencystock.application.StockService;
import org.example.concurrencystock.domain.Stock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public Stock decrease(Long id, Long quantity) throws InterruptedException {
        final RLock lock = redissonClient.getLock(id.toString());

        try {
            final boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("Lock not available");
            }

            return stockService.decrease(id, quantity);
        } finally {
            lock.unlock();
        }
    }
}
