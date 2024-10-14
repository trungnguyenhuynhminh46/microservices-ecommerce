package com.tuber.domain.exception;

import com.tuber.domain.constant.ResponseCode;

public class DomainException extends RuntimeException {
    private final int OK = 200;
    private final ResponseCode responseCode;

    private int statusCode = OK;

    public DomainException(ResponseCode responseCode, int statusCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return responseCode.getCode();
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
