package com.tuber.identity.service.domain.helper.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.entity.RevokedRefreshToken;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.exception.LoggedOutAlready;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.ports.output.repository.RevokedRefreshTokenRepository;
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
    RevokedRefreshTokenRepository revokedRefreshTokenRepository;
    CommonIdentityServiceHelper commonIdentityServiceHelper;
    AuthenticationHelper authenticationHelper;

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
                .issuer(issuer)
                .issueTime(new Date())
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

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        try {
            jwsObject.sign(macSigner);
        } catch (JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.UNCATEGORIZED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return jwsObject.serialize();
    }

    public String generateJwtAccessToken(UserAccount userAccount) {
        String token = generateJwtToken(
                userAccount.getUserId(),
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
                userAccount.getUserId(),
                "tuber.com",
                List.of(),
                REFRESHABLE_DURATION
        );
        log.info("Generated refresh token: {}", token);

        return token;
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean isValid = signedJWT.verify(macVerifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isExpired = expirationTime.before(new Date());
            String userId = signedJWT.getJWTClaimsSet().getSubject();

            boolean tokenIsValid = isValid && !isExpired;
            if (tokenIsValid) {
                commonIdentityServiceHelper.verifyUserAccountWithIdExist(UUID.fromString(userId));
            }

            return tokenIsValid;
        } catch (ParseException | JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    public UUID extractUserIdFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return UUID.fromString(signedJWT.getJWTClaimsSet().getSubject());
        } catch (ParseException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    public Date extractExpirationTimeFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Transactional
    public String rotateRefreshToken(String refreshToken) {
        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountWithIdExist(extractUserIdFromToken(refreshToken));
        RevokedRefreshToken revokedRefreshToken = RevokedRefreshToken.builder()
                .id(new RefreshTokenId(refreshToken))
                .userId(userAccount.getId().getValue())
                .expiresIn(extractExpirationTimeFromToken(refreshToken).toInstant())
                .build();

        revokedRefreshTokenRepository.save(revokedRefreshToken);
        return generateJwtRefreshToken(userAccount);
    }

    public void verifyAccessTokenAndRefreshTokenHaveSameCreator(String accessToken, String refreshToken) {
        UUID accessTokenUserId = extractUserIdFromToken(accessToken);
        UUID refreshTokenUserId = extractUserIdFromToken(refreshToken);
        if (!accessTokenUserId.equals(refreshTokenUserId)) {
            throw new IdentityDomainException(IdentityResponseCode.REFRESH_TOKEN_OF_ANOTHER_USER, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Transactional
    public void logout(String refreshToken) {
        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountWithIdExist(extractUserIdFromToken(refreshToken));
        RevokedRefreshToken revokedRefreshToken = RevokedRefreshToken.builder()
                .id(new RefreshTokenId(refreshToken))
                .userId(userAccount.getId().getValue())
                .expiresIn(extractExpirationTimeFromToken(refreshToken).toInstant())
                .build();

        revokedRefreshTokenRepository.save(revokedRefreshToken);
    }

    public void verifyRefreshToken(String refreshToken) {
        String accessToken = authenticationHelper.getAccessToken();
        Optional<RevokedRefreshToken> savedRefreshToken = revokedRefreshTokenRepository.findByToken(refreshToken);

        if (savedRefreshToken.isPresent()) {
            throw new LoggedOutAlready(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.ALREADY_REPORTED.value());
        }
        verifyAccessTokenAndRefreshTokenHaveSameCreator(accessToken, refreshToken);
    }
}
