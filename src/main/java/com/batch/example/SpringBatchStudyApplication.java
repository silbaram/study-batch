package com.batch.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchStudyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBatchStudyApplication.class, args);
        SpringApplication.exit(context);
    }
}
