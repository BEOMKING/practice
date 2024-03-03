package org.example.concurrencystock.facade;

import lombok.extern.slf4j.Slf4j;
import org.example.concurrencystock.application.OptimisticLockStockService;
import org.example.concurrencystock.domain.Stock;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OptimisticLockStockFacade {
    private final OptimisticLockStockService optimisticLockStockService;

    public OptimisticLockStockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    public Stock decrease(final Long id, final Long quantity) throws InterruptedException {
        while (true) {
            try {
                return optimisticLockStockService.decrease(id, quantity);
            } catch (Exception e) {
                log.info("Conflict lock");
                Thread.sleep(50);
            }
        }
    }
}
