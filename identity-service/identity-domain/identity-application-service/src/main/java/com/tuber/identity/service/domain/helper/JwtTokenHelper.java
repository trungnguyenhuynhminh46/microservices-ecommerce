package com.tuber.identity.service.domain.helper;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtTokenHelper {
    MACSigner macSigner;
    MACVerifier macVerifier;

    @NonFinal
    @Value("${jwt.access-token-lifetime}")
    long ACCESSIBLE_DURATION;

    @NonFinal
    @Value("${jwt.refresh-token-lifetime}")
    long REFRESHABLE_DURATION;

    private String buildScope(UserAccount userAccount) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(userAccount.getRoles())) {
            userAccount.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }

        return stringJoiner.toString();
    }

    public String generateJwtToken(String subject, String issuer, List<String> claims, long durationInSeconds) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512).build();

        JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("tuber.com")
                .expirationTime(new Date(
                        Instant.now().plus(durationInSeconds, ChronoUnit.SECONDS).toEpochMilli()
                ));
        claims.forEach(claim -> {
            String[] claimParts = claim.split(":");
            claimsSetBuilder.claim(claimParts[0], claimParts[1]);
        });
        JWTClaimsSet claimsSet = claimsSetBuilder.build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(macSigner);
        } catch (JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.UNCATEGORIZED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return jwsObject.serialize();
    }

    public String generateJwtAccessToken(UserAccount userAccount) {
        String token = generateJwtToken(
                userAccount.getId().toString(),
                "tuber.com",
                List.of(
                        "email:" + userAccount.getEmail(),
                        "scope:" + buildScope(userAccount)
                ),
                ACCESSIBLE_DURATION
        );
        log.info("Generated access token: {}", token);

        return token;
    }

    public String generateJwtRefreshToken() {
        String token = generateJwtToken(
                "refresh token",
                "tuber.com",
                List.of(),
                REFRESHABLE_DURATION
        );
        log.info("Generated refresh token: {}", token);

        return token;
    }
}
