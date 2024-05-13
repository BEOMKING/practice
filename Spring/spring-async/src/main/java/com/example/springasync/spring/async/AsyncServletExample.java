package com.example.springasync.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@EnableAsync
@SpringBootApplication
public class AsyncServletExample {

    private static final long MILLIS = 2000L;

    @RestController
    public static class MyController {
        @GetMapping("/async")
        public Callable<String> async() {
            log.info("async");
            return () -> {
                Thread.sleep(MILLIS);
                log.info("async processing end");
                return "async result";
            };
        }

//        public String async() throws InterruptedException {
//            log.info("async");
//            Thread.sleep(MILLIS);
//            log.info("async processing end");
//            return "async result";
//        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AsyncServletExample.class, args);
        log.info("MILLIS: {}", MILLIS);
    }
}
