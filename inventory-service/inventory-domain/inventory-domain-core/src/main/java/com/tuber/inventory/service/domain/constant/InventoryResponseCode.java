package com.tuber.inventory.service.domain.constant;

import com.tuber.domain.constant.ResponseCodeBase;

public class InventoryResponseCode extends ResponseCodeBase {
    protected final String serviceName = "inventory";
    public static final InventoryResponseCode SUCCESS_RESPONSE =
            new InventoryResponseCode(1000, "Your request is processed successfully!");

    public static final InventoryResponseCode UNCATEGORIZED_EXCEPTION =
            new InventoryResponseCode(9999, "Uncategorized error");

    public static final InventoryResponseCode PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1001, "Product is in wrong state for initialization");

    public static final InventoryResponseCode INVENTORY_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1002, "Inventory is in wrong state for initialization");

    protected InventoryResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
