package com.task.paydaytrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PaydayTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaydayTradeApplication.class, args);
    }

}
