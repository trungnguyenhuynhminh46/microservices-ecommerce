package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCode;

public class UserAccountNotFoundException extends IdentityDomainException {
    public UserAccountNotFoundException(ResponseCode responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
