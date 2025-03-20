package com.tuber.inventory.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Slf4j
@EnableFeignClients(basePackages = "com.tuber.inventory.service.domain.ports.output.http.client")
@EnableJpaRepositories(basePackages = {"com.tuber.inventory.service.dataaccess", "com.tuber.dataaccess"})
@EnableRedisRepositories(basePackages = {"com.tuber.inventory.service.dataaccess.caching", "com.tuber.dataaccess.caching"})
@EntityScan(basePackages = {"com.tuber.inventory.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
