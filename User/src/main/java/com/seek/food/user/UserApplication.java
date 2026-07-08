package com.seek.food.user;

import com.seek.food.config.Interface.CommonImport;
import com.seek.food.config.Interface.MQImport;
import com.seek.food.config.Interface.NativeServiceImport;
import com.seek.food.config.Interface.UserImport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@UserImport
@CommonImport
@MQImport
@EnableScheduling
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
