package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCodeBase;

public class AuthenticationException extends IdentityDomainException{
    public AuthenticationException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
