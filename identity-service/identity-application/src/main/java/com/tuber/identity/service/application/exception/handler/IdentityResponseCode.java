package com.tuber.identity.service.application.exception.handler;

import com.tuber.application.handler.ResponseCode;
import org.springframework.http.HttpStatus;

public class IdentityResponseCode extends ResponseCode {
    protected final String serviceName = "identity";
    protected IdentityResponseCode(int code, String message, HttpStatus status) {
        super(code, message, status);
    }
}