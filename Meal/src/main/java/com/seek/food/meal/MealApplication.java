package com.seek.food.meal;

import com.seek.food.config.Import.CommonImport;
import com.seek.food.config.Import.MQImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@CommonImport
@MQImport
@EnableScheduling
public class MealApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealApplication.class, args);
    }

}
