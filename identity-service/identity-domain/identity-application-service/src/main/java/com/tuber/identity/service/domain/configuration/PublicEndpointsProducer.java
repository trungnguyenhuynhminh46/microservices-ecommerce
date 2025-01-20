package com.tuber.identity.service.domain.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PublicEndpointsProducer {
    @Value("${service.name}")
    private String serviceName;

    @Getter
    private String[] publicEndpoints;

    @PostConstruct
    public void init() {
        String[] baseRoutes = {
                "/auth/register",
                "/auth/login"
        };

        publicEndpoints = Arrays.stream(baseRoutes)
                .map(route -> String.format("/%s%s", serviceName, route))
                .toArray(String[]::new);
    }
}
