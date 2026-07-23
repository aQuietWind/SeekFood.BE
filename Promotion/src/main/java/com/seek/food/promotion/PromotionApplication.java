package com.seek.food.promotion;

import com.seek.food.config.Import.CommonImport;
import com.seek.food.config.Import.MQImport;
import com.seek.food.config.Import.PromotionImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CommonImport
@MQImport
@PromotionImport
public class PromotionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionApplication.class, args);
    }

}
