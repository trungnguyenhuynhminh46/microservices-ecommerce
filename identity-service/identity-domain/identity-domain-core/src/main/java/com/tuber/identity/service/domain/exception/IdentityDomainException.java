package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.ResponseCodeBase;
import com.tuber.domain.exception.DomainException;

public class IdentityDomainException extends DomainException {
    protected final String serviceName = "identity";

    public IdentityDomainException(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
