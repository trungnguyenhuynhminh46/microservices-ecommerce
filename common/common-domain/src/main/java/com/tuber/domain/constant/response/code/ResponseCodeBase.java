package com.tuber.domain.constant.response.code;

public abstract class ResponseCodeBase {
    protected String code;
    protected String message;

    public abstract String getServiceName();

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected abstract String formatErrorCode(int code);
}
