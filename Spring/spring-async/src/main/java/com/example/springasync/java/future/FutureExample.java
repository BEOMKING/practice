package com.example.springasync.java.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        final Future<String> future = executorService.submit(() -> {
            Thread.sleep(2000L);
            log.info("Async");
            return "Hello";
        });

        System.out.println(future.isDone());
        log.info("Exit");
        System.out.println(future.isDone());
        log.info(future.get()); // Blocking 처리 될 때까지 기다림

        executorService.shutdown();
    }
}
