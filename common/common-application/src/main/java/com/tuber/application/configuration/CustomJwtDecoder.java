package com.tuber.application.configuration;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private Instant convertToInstant(String time) {
        if (time == null || time.isEmpty()) {
            throw new IllegalArgumentException("Input time cannot be null or empty");
        }

        try {
            long timestamp = Long.parseLong(time);
            return Instant.ofEpochSecond(timestamp);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time format, must be a Unix timestamp: " + time, e);
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
            throw new JwtException("Invalid token");
        }
    }
}
