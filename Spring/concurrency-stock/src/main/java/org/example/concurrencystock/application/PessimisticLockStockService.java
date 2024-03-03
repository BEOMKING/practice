package org.example.concurrencystock.application;

import org.example.concurrencystock.domain.Stock;
import org.example.concurrencystock.domain.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticLockStockService {
    private final StockRepository stockRepository;

    public PessimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Stock decrease(final Long id, final Long quantity) {
        final Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);
        return stockRepository.save(stock);
    }
}
