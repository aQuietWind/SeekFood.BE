package com.seek.food.merchant;

import com.seek.food.config.Import.CommonImport;
import com.seek.food.config.Import.MQImport;
import com.seek.food.config.Import.MerchantImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@CommonImport
@MQImport
@MerchantImport
@EnableScheduling
public class MerchantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class, args);
    }

}
