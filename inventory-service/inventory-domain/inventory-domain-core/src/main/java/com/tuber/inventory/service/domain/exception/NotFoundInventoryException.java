package com.tuber.inventory.service.domain.exception;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;

import java.util.UUID;

public class NotFoundInventoryException extends InventoryDomainException {
    private final UUID inventoryId;
    public NotFoundInventoryException(UUID inventoryId, InventoryResponseCode responseCode, int statusCode, Object... args) {
        super(responseCode, statusCode, args);
        this.inventoryId = inventoryId;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }
}
