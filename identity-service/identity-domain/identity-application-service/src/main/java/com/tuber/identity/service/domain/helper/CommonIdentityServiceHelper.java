package com.tuber.identity.service.domain.helper;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.AuthenticationException;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonIdentityServiceHelper {
    UserAccountRepository userAccountRepository;
    PasswordEncoder passwordEncoder;
    MACVerifier macVerifier;

    public UserAccount verifyUserAccountWithUsernameExist(String username) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(username);
        if (userAccount.isEmpty()) {
            log.warn("Could not find user account with username: {}", username);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

        return userAccount.get();
    }

    public UserAccount verifyUserAccountWithIdExist(UUID userId) {
        Optional<UserAccount> userAccount = userAccountRepository.findById(userId);
        if (userAccount.isEmpty()) {
            log.warn("Could not find user account with id: {}", userId);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_ID_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return userAccount.get();
    }

    public void verifyPassword(String rawPassword, String encodedPassword) {
        boolean matched = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!matched) {
            throw new AuthenticationException(IdentityResponseCode.FAILED_AUTHENTICATION, HttpStatus.FORBIDDEN.value());
        }
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean isValid = signedJWT.verify(macVerifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isExpired = expirationTime.before(new Date());

            return isValid && !isExpired;
        } catch (ParseException | JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_FORMAT_JWT_TOKEN, HttpStatus.BAD_REQUEST.value());
        }
    }
}
