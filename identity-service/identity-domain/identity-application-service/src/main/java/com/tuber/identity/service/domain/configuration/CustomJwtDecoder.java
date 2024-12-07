package com.tuber.identity.service.domain.configuration;

import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.exception.RefreshTokenNotFoundException;
import com.tuber.identity.service.domain.helper.authentication.JwtTokenHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomJwtDecoder implements JwtDecoder {
    NimbusJwtDecoder nimbusJwtDecoder;
    JwtTokenHelper jwtTokenHelper;

    @Override
    public Jwt decode(String accessToken) throws JwtException {
        if (!jwtTokenHelper.verifyToken(accessToken)) {
            throw new RefreshTokenNotFoundException(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.NOT_FOUND.value());
        }

        return nimbusJwtDecoder.decode(accessToken);
    }
}
