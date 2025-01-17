package com.tuber.profile.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication(scanBasePackages = "com.tuber")
@EntityScan(basePackages = {"com.tuber.profile.service.dataaccess", "com.tuber.dataaccess"})
@EnableNeo4jRepositories(basePackages = {"com.tuber.profile.service.dataaccess", "com.tuber.dataaccess"})
public class ProfileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }
}
