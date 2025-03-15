package com.tuber.order.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableFeignClients(basePackages = "com.tuber.order.service.domain.ports.output.http.client")
@EnableJpaRepositories(basePackages = {"com.tuber.order.service.dataaccess", "com.tuber.dataaccess"})
@EntityScan(basePackages = {"com.tuber.order.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
