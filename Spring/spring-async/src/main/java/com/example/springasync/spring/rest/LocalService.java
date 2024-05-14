package com.example.springasync.spring.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableAsync
public class LocalService {

    @RestController
    public static class LocalController {
        final RestTemplate restTemplate = new RestTemplate();

        @GetMapping("/local")
        public String local(int idx) {
            log.info("local");
            return restTemplate.getForObject("http://localhost:8081/remote?req={req}", String.class, "local/" + idx);
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        System.setProperty("server.tomcat.max-threads", "1");
        SpringApplication.run(LocalService.class, args);
    }
}
