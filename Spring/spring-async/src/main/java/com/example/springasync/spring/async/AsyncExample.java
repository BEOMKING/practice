//package com.example.springasync.spring.async;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//
//@Slf4j
//@EnableAsync
//@SpringBootApplication
//public class AsyncExample {
//    @Component
//    public static class AsyncService {
//        @Async
//        public ListenableFuture<String> asyncMethod() throws InterruptedException {
//            log.info("Async");
//            Thread.sleep(2000L);
//            return new AsyncResult<>("async result");
//        }
//    }
//
//    @Bean
//    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(10);
//        threadPoolTaskExecutor.setMaxPoolSize(100);
//        threadPoolTaskExecutor.setQueueCapacity(200);
//        threadPoolTaskExecutor.setThreadNamePrefix("myThread-");
//        threadPoolTaskExecutor.initialize();
//        return threadPoolTaskExecutor;
//    }
//
//    @Autowired
//    private AsyncService asyncService;
//
//    @Bean
//    ApplicationRunner applicationRunner() {
//        return args -> {
//            log.info("run");
//            final ListenableFuture<String> future = asyncService.asyncMethod();
//            future.addCallback(s -> log.info("result {}", s), e -> log.error("error", e));
//            log.info("exit");
//        };
//    }
//
//    public static void main(String[] args) {
//        try (final ConfigurableApplicationContext run = SpringApplication.run(AsyncExample.class, args)) {
//
//        }
//    }
//}
