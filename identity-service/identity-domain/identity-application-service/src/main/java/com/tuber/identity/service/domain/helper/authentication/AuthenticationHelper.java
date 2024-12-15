package com.tuber.identity.service.domain.helper.authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthenticationHelper {
    private final Authentication authentication;

    public AuthenticationHelper() {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public AbstractOAuth2Token getCurrentPrincipalName() {
        return (AbstractOAuth2Token) authentication.getPrincipal();
    }

    public String getAccessToken() {
        return getCurrentPrincipalName().getTokenValue();
    }

    public Instant getIssuedAt() {
        return getCurrentPrincipalName().getIssuedAt();
    }

    public Instant getExpiresAt() {
        return getCurrentPrincipalName().getExpiresAt();
    }
}
