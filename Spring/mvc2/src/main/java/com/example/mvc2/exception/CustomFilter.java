package com.example.mvc2.exception;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class CustomFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // URI 를 로그로 남긴다.
        log.info("doFilter: {}", ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(request, response);
    }
}
