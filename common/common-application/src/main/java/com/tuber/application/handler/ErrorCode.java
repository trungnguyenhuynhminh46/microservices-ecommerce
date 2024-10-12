package com.tuber.application.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String serviceName = "common";
    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = formatErrorCode(code);
        this.message = message;
        this.status = status;
    }

    private String formatErrorCode(int code) {
        return String.format("%s_%d", serviceName, code);
    }
}
