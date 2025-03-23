package com.tuber.domain.exception;

import com.tuber.domain.constant.response.code.ResponseCodeBase;
import com.tuber.domain.exception.DomainException;

public class ProductDomainException extends DomainException {
    protected final String serviceName = "product";
    public ProductDomainException(ResponseCodeBase responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
