package org.example.concurrencystock.application;

import org.example.concurrencystock.domain.Stock;
import org.example.concurrencystock.domain.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//    /**
//     * <p>@Transactional 애노테이션에 의해 synchronized 키워드가 사용되지 않는다.
//     *
//     * @see org.example.concurrencystock.application.TransactionStockService
//     */
////    @Transactional
//    public synchronized Stock decrease(final Long id, final Long quantity) {
//        final Stock stock = stockRepository.findById(id).orElseThrow();
//        stock.decrease(quantity);
//        return  stockRepository.saveAndFlush(stock);
//    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Stock decrease(final Long id, final Long quantity) {
        final Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        return  stockRepository.saveAndFlush(stock);
    }
}
