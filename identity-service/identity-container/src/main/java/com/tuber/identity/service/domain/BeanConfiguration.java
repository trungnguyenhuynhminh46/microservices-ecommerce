package com.tuber.identity.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public IdentityDomainService identityDomainService() {
        return new IdentityDomainServiceImpl();
    }
}
