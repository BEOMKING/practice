package com.example.springasync.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(100);
        final RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/async";

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                int idx = counter.addAndGet(1);
                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                restTemplate.getForObject(url, String.class);
                sw.stop();

                log.info("Elapsed: {} {}", idx, sw.getTotalTimeSeconds());
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, java.util.concurrent.TimeUnit.SECONDS);

        stopWatch.stop();
        log.info("Total: {}", stopWatch.getTotalTimeSeconds());
    }
}
