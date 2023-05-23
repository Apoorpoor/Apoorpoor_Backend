package com.example.apoorpoor_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApoorpoorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApoorpoorBackendApplication.class, args);
    }

}
