package com.tuber.profile.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;
import com.tuber.domain.exception.DomainException;

public class ProfileDomainException extends DomainException {
    protected final String serviceName = "profile";
    public ProfileDomainException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
