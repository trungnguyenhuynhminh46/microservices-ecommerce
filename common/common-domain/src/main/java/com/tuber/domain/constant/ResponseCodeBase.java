package com.tuber.domain.constant;

public abstract class ResponseCodeBase {
    protected String code;
    protected String message;

    public abstract String getServiceName();

    public abstract String getCode();

    public abstract String getMessage();

    protected abstract String formatErrorCode(int code);
}
