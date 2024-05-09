package com.example.springasync.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Slf4j
@EnableAsync
@SpringBootApplication
public class AsyncExample {
    @Component
    public static class AsyncService {
        @Async
        public Future<String> asyncMethod() throws InterruptedException {
            log.info("Async");
            Thread.sleep(2000L);
            return new AsyncResult<>("async result");
        }
    }

    @Autowired
    private AsyncService asyncService;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            log.info("run");
            final Future<String> stringFuture = asyncService.asyncMethod();
            log.info("exit {}", stringFuture.isDone());
            log.info("result {}", stringFuture.get());
        };
    }

    public static void main(String[] args) {
        try (final ConfigurableApplicationContext run = SpringApplication.run(AsyncExample.class, args)) {

        }
    }
}
