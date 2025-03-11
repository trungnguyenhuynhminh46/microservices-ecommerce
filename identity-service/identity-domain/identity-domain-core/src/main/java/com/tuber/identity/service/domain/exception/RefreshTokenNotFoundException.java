package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class RefreshTokenNotFoundException extends IdentityDomainException {
    public RefreshTokenNotFoundException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
