package com.tuber.application.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseCode {
    public static final ResponseCode SUCCESS_RESPONSE =
            new ResponseCode(1000, "Your request is processed successfully!", HttpStatus.OK);
    public static final ResponseCode UNCATEGORIZED_EXCEPTION =
            new ResponseCode(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    protected final String serviceName = "common";
    private final String code;
    private final String message;
    private final HttpStatus status;

    protected ResponseCode(int code, String message, HttpStatus status) {
        this.code = formatErrorCode(code);
        this.message = message;
        this.status = status;
    }

    private String formatErrorCode(int code) {
        return String.format("%s_%d", serviceName, code);
    }
}
