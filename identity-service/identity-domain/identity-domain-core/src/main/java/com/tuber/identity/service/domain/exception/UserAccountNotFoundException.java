package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCodeBase;

public class UserAccountNotFoundException extends IdentityDomainException {
    public UserAccountNotFoundException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
