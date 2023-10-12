package com.phototeca.cryptocurrencybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptocurrencybotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencybotApplication.class, args);
    }

}
