package com.task.paydaytrade;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT3M")
@EnableFeignClients
@SpringBootApplication
public class PaydayTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaydayTradeApplication.class, args);
    }

}
