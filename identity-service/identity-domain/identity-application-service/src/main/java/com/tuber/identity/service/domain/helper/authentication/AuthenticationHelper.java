package com.tuber.identity.service.domain.helper.authentication;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.exception.LoggedOutAlready;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthenticationHelper {
    public AbstractOAuth2Token getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new LoggedOutAlready(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.ALREADY_REPORTED.value());
        }
        return (AbstractOAuth2Token) authentication.getPrincipal();
    }

    public String getAccessToken() {
        return getPrincipal().getTokenValue();
    }

    public Instant getIssuedAt() {
        return getPrincipal().getIssuedAt();
    }

    public Instant getExpiresAt() {
        return getPrincipal().getExpiresAt();
    }
}
