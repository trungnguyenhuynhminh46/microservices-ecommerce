package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class RoleNotFoundException extends IdentityDomainException {
    public RoleNotFoundException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
