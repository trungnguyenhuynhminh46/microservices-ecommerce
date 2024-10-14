package com.tuber.domain.constant;

public class ResponseCode {
    public static final ResponseCode SUCCESS_RESPONSE =
            new ResponseCode(1000, "Your request is processed successfully!");
    public static final ResponseCode UNCATEGORIZED_EXCEPTION =
            new ResponseCode(9999, "Uncategorized error");

    protected final String serviceName = "common";
    private final String code;
    private final String message;

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

    private String formatErrorCode(int code) {
        return String.format("%s_%d", serviceName, code);
    }
}
