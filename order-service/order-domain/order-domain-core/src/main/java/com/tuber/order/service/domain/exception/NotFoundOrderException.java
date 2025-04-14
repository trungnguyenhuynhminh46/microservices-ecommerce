package com.tuber.order.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;
import com.tuber.domain.exception.OrderDomainException;

public class NotFoundOrderException extends OrderDomainException {
    public NotFoundOrderException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
