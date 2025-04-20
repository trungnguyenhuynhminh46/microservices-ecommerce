package com.tuber.order.service.domain.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "config-data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceConfigurationData {
    String paymentRequestTopicName;
    String paymentResponseTopicName;
    String inventoryConfirmationRequestTopicName;
    String inventoryResponseTopicName;
}
