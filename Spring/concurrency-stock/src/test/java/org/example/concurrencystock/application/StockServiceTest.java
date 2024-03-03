package org.example.concurrencystock.application;

import org.example.concurrencystock.domain.Stock;
import org.example.concurrencystock.domain.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {
    @Autowired
//    private StockService stockService;
    private PessimisticLockStockService stockService;


    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    void decrease() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
        stockService.decrease(2L, 1L);
        Stock stock = stockRepository.findById(2L).orElseThrow();

        assertThat(stock.getQuantity()).isEqualTo(99L);
    }

    @Test
    void concurrentDecrease() throws InterruptedException {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
        final ExecutorService executorService = Executors.newFixedThreadPool(32);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final Stock stock = stockService.decrease(1L, 1L);
                System.out.println(stock.getQuantity());
            });
        }

        Thread.sleep(3000L);

        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(0L);
    }
}