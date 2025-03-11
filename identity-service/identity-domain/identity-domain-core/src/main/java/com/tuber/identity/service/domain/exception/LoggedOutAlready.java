package com.tuber.identity.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class LoggedOutAlready extends IdentityDomainException {
    public LoggedOutAlready(ResponseCodeBase responseCode, int statusCode) {
        super(responseCode, statusCode);
    }
}
