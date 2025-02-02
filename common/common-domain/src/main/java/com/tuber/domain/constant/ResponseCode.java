package com.tuber.domain.constant;

public class ResponseCode extends ResponseCodeBase {
    protected final String serviceName = "common";
    public static final ResponseCode SUCCESS_RESPONSE =
            new ResponseCode(1000, "Your request is processed successfully!");
    public static final ResponseCode UNCATEGORIZED_EXCEPTION =
            new ResponseCode(9999, "Uncategorized error");
    public static final ResponseCode VALIDATION_ERROR =
            new ResponseCode(1001, "Validation error");
    public static final ResponseCode INVALID_REQUEST_PARAM_VALUE =
            new ResponseCode(1002, "Invalid request parameter value");
    public static final ResponseCode INVALID_REQUEST_BODY =
            new ResponseCode(1003, "Invalid request body");
    public static final ResponseCode UNAUTHENTICATED =
            new ResponseCode(1004, "You are not authenticated");
    public static final ResponseCode INVALID_TOKEN =
            new ResponseCode(1005, "Invalid token");
    public static final ResponseCode AUTHORIZATION_DENIED =
            new ResponseCode(1006, "You need permission to perform this action");

    protected String code;
    protected String message;

    protected ResponseCode() {
    }

    protected ResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public ResponseCode(String formattedCode, String message) {
        this.code = formattedCode;
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
