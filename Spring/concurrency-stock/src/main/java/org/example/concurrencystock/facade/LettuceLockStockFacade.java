package org.example.concurrencystock.facade;

import org.example.concurrencystock.application.StockService;
import org.example.concurrencystock.domain.RedisLockRepository;
import org.example.concurrencystock.domain.Stock;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public LettuceLockStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
        this.redisLockRepository = redisLockRepository;
        this.stockService = stockService;
    }

    public Stock decrease(Long id, Long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(id)) {
                Thread.sleep(100);
        }

        try {
            return stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
