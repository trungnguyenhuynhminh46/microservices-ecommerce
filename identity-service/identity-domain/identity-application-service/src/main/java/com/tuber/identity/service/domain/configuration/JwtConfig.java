package com.tuber.identity.service.domain.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class JwtConfig {
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Bean
    MACSigner macSigner() {
        try {
            return new MACSigner(SIGNER_KEY.getBytes());
        } catch (JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_SIGNER_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Bean
    MACVerifier macVerifier() {
        try {
            return new MACVerifier(SIGNER_KEY.getBytes());
        } catch (JOSEException e) {
            throw new IdentityDomainException(IdentityResponseCode.INVALID_SIGNER_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
