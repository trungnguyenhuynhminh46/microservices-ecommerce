package com.tuber.domain.exception;

import com.tuber.domain.constant.ResponseCode;

public class DomainException extends RuntimeException {
    private final ResponseCode responseCode;

    public DomainException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
