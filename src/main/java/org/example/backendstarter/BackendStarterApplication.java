package org.example.backendstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackendStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendStarterApplication.class, args);
    }

}
