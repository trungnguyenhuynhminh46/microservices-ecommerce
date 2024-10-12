package com.tuber.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    private static final int PasswordStrength = 10;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(SecurityConfig.PasswordStrength);
    }
}
