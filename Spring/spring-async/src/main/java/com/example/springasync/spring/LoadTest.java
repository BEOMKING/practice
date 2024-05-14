package com.example.springasync.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        final ExecutorService executorService = Executors.newFixedThreadPool(100);
        final RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/local?idx={idx}";

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                int idx = counter.addAndGet(1);
                cyclicBarrier.await();

                log.info("Thread {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                final String result = restTemplate.getForObject(url, String.class, idx);
                sw.stop();

                log.info("Elapsed: {} {} {}", idx, sw.getTotalTimeSeconds(), result);
                return null;
            });
        }

        cyclicBarrier.await();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        executorService.shutdown();
        executorService.awaitTermination(100, java.util.concurrent.TimeUnit.SECONDS);

        stopWatch.stop();
        log.info("Total: {}", stopWatch.getTotalTimeSeconds());
    }
}
