package com.tuber.payment.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;
import com.tuber.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {
    protected final String serviceName = "payment";

    public PaymentDomainException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }

    public PaymentDomainException(ResponseCodeBase responseCode, int statusCode, Throwable cause, Object... args) {
        super(responseCode, statusCode, cause, args);
    }
}
