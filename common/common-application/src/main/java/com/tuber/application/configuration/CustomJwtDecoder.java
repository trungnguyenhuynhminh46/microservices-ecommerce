package com.tuber.application.configuration;

import com.nimbusds.jwt.SignedJWT;
import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private Instant convertToInstant(String time) {
        try {
            return (time == null || time.isEmpty()) ? null : Instant.ofEpochSecond(Long.parseLong(time));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Instant expirationTime = convertToInstant(signedJWT.getPayload().toJSONObject().get("exp").toString());
            Instant issueTime = convertToInstant((signedJWT.getPayload().toJSONObject().get("iat").toString()));
            return new Jwt(token,
                    issueTime,
                    expirationTime,
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims()
            );

        } catch (ParseException e) {
            throw new DomainException(ResponseCode.INVALID_TOKEN, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
