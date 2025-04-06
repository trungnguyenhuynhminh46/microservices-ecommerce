package com.tuber.order.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "config-data")
public class OrderServiceConfigurationData {
    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
}
