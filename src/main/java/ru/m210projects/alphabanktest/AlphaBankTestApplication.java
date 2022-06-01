package ru.m210projects.alphabanktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition
public class AlphaBankTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlphaBankTestApplication.class, args);
    }

}
