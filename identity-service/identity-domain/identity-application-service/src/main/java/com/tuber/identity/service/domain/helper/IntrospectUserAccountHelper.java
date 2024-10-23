package com.tuber.identity.service.domain.helper;

import com.nimbusds.jwt.SignedJWT;
import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.IntrospectUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.IntrospectUserAccountResponseData;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IntrospectUserAccountHelper {
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    private IntrospectUserAccountResponseData buildIntrospectUserAccountResponseData(IntrospectUserAccountCommand introspectUserAccountCommand) {
        boolean isActive = commonIdentityServiceHelper.verifyToken(introspectUserAccountCommand.getAccessToken());
        if (isActive) {
            log.info("Token is active");
            try {
                SignedJWT signedJWT = SignedJWT.parse(introspectUserAccountCommand.getAccessToken());
                return IntrospectUserAccountResponseData.builder()
                        .issuer(signedJWT.getJWTClaimsSet().getIssuer())
                        .userId(signedJWT.getJWTClaimsSet().getSubject())
                        .expirationTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                        .active(true)
                        .build();
            } catch (ParseException e) {
                throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
            }
        }
        return IntrospectUserAccountResponseData.builder()
                .active(false)
                .build();
    }

    public ApiResponse<IntrospectUserAccountResponseData> introspect(IntrospectUserAccountCommand introspectUserAccountCommand) {
        return ApiResponse.<IntrospectUserAccountResponseData>builder()
                .message("User account introspect result")
                .data(buildIntrospectUserAccountResponseData(introspectUserAccountCommand))
                .build();
    }
}
