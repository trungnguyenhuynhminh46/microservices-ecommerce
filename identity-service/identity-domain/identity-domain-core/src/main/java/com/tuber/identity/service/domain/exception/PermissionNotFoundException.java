package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCodeBase;

public class PermissionNotFoundException extends IdentityDomainException{
    public PermissionNotFoundException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
