package com.tuber.payment.service.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class NotFoundPaymentException extends PaymentDomainException{
    public NotFoundPaymentException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
