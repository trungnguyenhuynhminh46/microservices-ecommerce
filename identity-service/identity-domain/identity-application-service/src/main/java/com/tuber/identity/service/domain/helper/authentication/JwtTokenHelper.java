package com.tuber.identity.service.domain.helper.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.entity.RefreshToken;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.exception.RefreshTokenNotFoundException;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.ports.output.repository.RefreshTokenRepository;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtTokenHelper {
    MACSigner macSigner;
    MACVerifier macVerifier;
    RefreshTokenRepository refreshTokenRepository;
    UserAccountRepository userAccountRepository;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

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
                stringJoiner.add("ROLE_" + role.getName().toString());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName().toString());
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
            if (claimParts.length == 2) {
                claimsSetBuilder.claim(claimParts[0], claimParts[1]);
            }

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
                userAccount.getUsername(),
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

    public String generateJwtRefreshToken(UserAccount userAccount) {
        String token = generateJwtToken(
                userAccount.getUsername(),
                "tuber.com",
                List.of(),
                REFRESHABLE_DURATION
        );
        log.info("Generated refresh token: {}", token);

        return token;
    }

    @Transactional
    public void persistRefreshToken(RefreshToken refreshToken) {
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        if (savedRefreshToken == null) {
            log.error("Failed to save refresh token: {}", refreshToken);
            throw new IdentityDomainException(IdentityResponseCode.REFRESH_TOKEN_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean isValid = signedJWT.verify(macVerifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isExpired = expirationTime.before(new Date());
            String username = signedJWT.getJWTClaimsSet().getSubject();

            boolean tokenIsValid = isValid && !isExpired;
            if (tokenIsValid) {
                commonIdentityServiceHelper.verifyUserAccountWithUsernameExist(username);
            }

            return tokenIsValid;
        } catch (ParseException | JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    public String extractSubjectFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    public UserAccount verifyUserAccountExists(String username) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(username);
        if (userAccount.isEmpty()) {
            log.warn("Could not find user account with username: {}", username);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return userAccount.get();
    }

    private RefreshToken persistNewRefreshToken(String oldRefreshToken) {
        String username = extractSubjectFromToken(oldRefreshToken);
        UserAccount userAccount = verifyUserAccountExists(username);
        RefreshToken newRefreshToken = RefreshToken.builder()
                .id(new RefreshTokenId(generateJwtRefreshToken(userAccount)))
                .userId(userAccount.getId().getValue())
                .isRevoked(false)
                .build();

        return refreshTokenRepository.save(newRefreshToken);
    }

    @Transactional
    public String rotateRefreshToken(String refreshToken) {
        if (!refreshTokenRepository.existsByTokenAndIsRevoked(refreshToken, false)) {
            throw new RefreshTokenNotFoundException(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.NOT_FOUND.value());
        }
        refreshTokenRepository.deleteByToken(refreshToken);

        RefreshToken savedRefreshToken = persistNewRefreshToken(refreshToken);
        return savedRefreshToken.getId().getValue();
    }
}
