package org.example.concurrencystock.facade;

import org.example.concurrencystock.application.StockService;
import org.example.concurrencystock.domain.LockRepository;
import org.example.concurrencystock.domain.Stock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    public NamedLockStockFacade(final LockRepository lockRepository, final StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public Stock decrease(final Long id, final Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            return stockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }

}
