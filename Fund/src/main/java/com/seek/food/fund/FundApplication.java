package com.seek.food.fund;

import com.seek.food.config.Interface.CommonImport;
import com.seek.food.config.Interface.FundImport;
import com.seek.food.config.Interface.MQImport;
import com.seek.food.config.Interface.UserImport;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MQImport
@CommonImport
@FundImport
public class FundApplication {
    public static void main(String[] args) {
        SpringApplication.run(FundApplication.class, args);
    }
}
