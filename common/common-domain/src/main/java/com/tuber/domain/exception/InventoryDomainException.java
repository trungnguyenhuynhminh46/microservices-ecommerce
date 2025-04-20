package com.tuber.domain.exception;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.constant.response.code.ResponseCode;

public class InventoryDomainException extends DomainException {
    protected final String serviceName = "inventory";
    public InventoryDomainException(InventoryResponseCode responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }

    public InventoryDomainException(ResponseCode responseCode, int statusCode, Exception exception) {
        super(responseCode, statusCode, exception);
    }
}
