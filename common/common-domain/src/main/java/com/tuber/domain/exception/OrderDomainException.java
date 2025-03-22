package com.tuber.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;
import com.tuber.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    protected final String serviceName = "order";

    public OrderDomainException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
