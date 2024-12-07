package com.tuber.identity.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {
    static int PasswordStrength = 10;
    @Bean
    public IdentityDomainService identityDomainService() {
        return new IdentityDomainServiceImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PasswordStrength);
    }
}
