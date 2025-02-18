package com.tuber.inventory.service.domain.exception;

import com.tuber.domain.exception.DomainException;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;

public class InventoryDomainException extends DomainException {
    protected final String serviceName = "inventory";
    public InventoryDomainException(InventoryResponseCode responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
    }
}
