package com.example.eco_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoServiceApplication.class, args);
        System.out.println("Swagger:   http://localhost:8080/swagger-ui/index.html");
    }

}
