package org.example.concurrencystock.application;

import org.example.concurrencystock.domain.Stock;
import org.example.concurrencystock.domain.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transaction 애노테이션은 실제로 StockService 클래스를 래핑하여 프록시 객체를 생성하여 동작한다.
 * 따라서 트랜잭션이 종료되기 전에 다른 스레드에서 decrease 메서드에 접근할 수 있다.
 */
@Service
public class TransactionStockService {
    private final StockService stockService;

    public TransactionStockService(StockRepository stockRepository) {
        this.stockService = new StockService(stockRepository);
    }

    public Stock decrease(final Long id, final Long quantity) {
        startTransaction();
        final Stock decrease = stockService.decrease(id, quantity);
        endTransaction();
        return decrease;
    }

    private void startTransaction() {
        System.out.println("Transaction started");
    }

    private void endTransaction() {
        System.out.println("Transaction ended");
    }
}
