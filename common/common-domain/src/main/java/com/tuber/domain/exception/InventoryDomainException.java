package com.tuber.domain.exception;

import com.tuber.domain.exception.DomainException;
import com.tuber.domain.constant.InventoryResponseCode;

public class InventoryDomainException extends DomainException {
    protected final String serviceName = "inventory";
    public InventoryDomainException(InventoryResponseCode responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
