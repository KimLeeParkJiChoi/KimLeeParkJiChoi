package com.sparta.spangeats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpangEatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpangEatsApplication.class, args);
    }


}
