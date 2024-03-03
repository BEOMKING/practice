package org.example.concurrencystock.facade;

import org.example.concurrencystock.domain.Stock;
import org.example.concurrencystock.domain.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LettuceLockStockFacadeTest {

    @Autowired
    private LettuceLockStockFacade stockService;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    void concurrentDecrease() throws InterruptedException {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
        final ExecutorService executorService = Executors.newFixedThreadPool(32);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    return stockService.decrease(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Thread.sleep(5000L);

        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(0L);
    }

}