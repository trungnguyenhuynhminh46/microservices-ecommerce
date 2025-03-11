package com.tuber.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class DomainException extends RuntimeException {
    private final int OK = 200;
    private final ResponseCodeBase responseCode;

    private int statusCode = OK;

    public DomainException(ResponseCodeBase responseCode, int statusCode, Object ...args) {
        super(String.format(responseCode.getMessage(), args));
        this.responseCode = responseCode;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return responseCode.getCode();
    }

    public ResponseCodeBase getResponseCode() {
        return responseCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
