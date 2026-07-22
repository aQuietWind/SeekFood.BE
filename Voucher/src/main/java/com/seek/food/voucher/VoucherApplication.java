package com.seek.food.voucher;

import com.seek.food.config.Import.CommonImport;
import com.seek.food.config.Import.MQImport;
import com.seek.food.config.Import.VoucherImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MQImport
@CommonImport
@VoucherImport
public class VoucherApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

}
