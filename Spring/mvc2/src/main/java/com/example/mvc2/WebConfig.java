package com.example.mvc2;

import com.example.mvc2.exception.CustomInterceptor;
import com.example.mvc2.exception.MyHandlerExceptionResolver;
import com.example.mvc2.exception.UserHandlerExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendHandlerExceptionResolvers(final List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor());
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addFormatter(new ItemFormatter(new ObjectMapper()));
    }
}
