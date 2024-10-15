package com.tuber.domain.constant;

public class ResponseCode {
    public static final ResponseCode SUCCESS_RESPONSE =
            new ResponseCode(1000, "Your request is processed successfully!");
    public static final ResponseCode UNCATEGORIZED_EXCEPTION =
            new ResponseCode(9999, "Uncategorized error");
    public static final ResponseCode VALIDATION_ERROR =
            new ResponseCode(1001, "Validation error");

    protected final String serviceName = "common";
    protected String code;
    protected String message;

    protected ResponseCode() {
    }

    protected ResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", serviceName, code);
    }
}
