package com.seek.food.admin;

import com.seek.food.config.Import.AdminImport;
import com.seek.food.config.Import.CommonImport;
import com.seek.food.config.Import.MQImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients         //开启Feign功能
@MQImport
@CommonImport
@EnableScheduling
@AdminImport
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
