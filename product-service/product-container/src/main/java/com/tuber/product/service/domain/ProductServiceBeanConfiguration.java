package com.tuber.product.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductServiceBeanConfiguration {
    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }
}
