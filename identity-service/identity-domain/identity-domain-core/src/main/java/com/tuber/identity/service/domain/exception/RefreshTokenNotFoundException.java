package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCodeBase;

public class RefreshTokenNotFoundException extends IdentityDomainException {
    public RefreshTokenNotFoundException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
