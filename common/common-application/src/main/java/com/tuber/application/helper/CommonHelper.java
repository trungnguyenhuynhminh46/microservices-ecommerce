package com.tuber.application.helper;

import com.nimbusds.jwt.SignedJWT;
import com.tuber.domain.constant.ResponseCode;
import com.tuber.domain.exception.DomainException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.ParseException;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonHelper {
    public String getHeaderValue(String headerName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest().getHeader(headerName);
    }

    public String getAuthorizationToken() {
        String authHeader = getHeaderValue("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

    public String extractTokenSubject() {
        String token = getAuthorizationToken();
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new DomainException(ResponseCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED.value());
        }
    }
}
