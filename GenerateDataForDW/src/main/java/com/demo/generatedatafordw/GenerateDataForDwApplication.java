package com.demo.generatedatafordw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GenerateDataForDwApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerateDataForDwApplication.class, args);
    }

}
