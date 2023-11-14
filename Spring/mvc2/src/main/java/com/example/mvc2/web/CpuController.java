package com.example.mvc2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cpu")
public class CpuController {
    @GetMapping("/cpu")
    public void cpu() {
        long start = System.currentTimeMillis();
        while (true) {
            final long l = System.currentTimeMillis() - start;
            System.out.println(l);
            if (l > 600000L) {
                break;
            }
        }
    }
}
