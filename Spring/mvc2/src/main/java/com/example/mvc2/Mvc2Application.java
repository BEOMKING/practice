package com.example.mvc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class Mvc2Application {

	public static void main(String[] args) {
		SpringApplication.run(Mvc2Application.class, args);
	}

//	@Bean
//	public MessageSource messageSource() {
//		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBasenames("messages", "errors");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
}
