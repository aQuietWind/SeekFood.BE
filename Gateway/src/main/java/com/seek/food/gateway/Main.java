package com.seek.food.gateway;

import com.seek.food.config.Interface.CommonImport;
import com.seek.food.config.Interface.GatewayImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@GatewayImport
@CommonImport
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
