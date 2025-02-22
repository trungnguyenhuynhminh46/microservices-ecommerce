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

    public static final InventoryResponseCode WAREHOUSE_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1003, "Warehouse is in wrong state for initialization");

    public static final InventoryResponseCode INVENTORY_TRANSACTION_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1004, "Inventory transaction is in wrong state for initialization");

    public static final InventoryResponseCode WAREHOUSE_NOT_FOUND =
            new InventoryResponseCode(1005, "Warehouse with id %s is not found");
    public static final InventoryResponseCode WAREHOUSE_SAVED_FAILED = new InventoryResponseCode(1005, "Failed to save warehouse %s");

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
