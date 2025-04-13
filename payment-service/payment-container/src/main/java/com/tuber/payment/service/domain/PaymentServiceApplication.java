package com.tuber.payment.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableJpaRepositories(basePackages = {"com.tuber.payment.service.dataaccess", "com.tuber.dataaccess"})
@EntityScan(basePackages = {"com.tuber.payment.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
