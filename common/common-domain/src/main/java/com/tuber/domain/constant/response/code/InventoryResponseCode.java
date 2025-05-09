package com.tuber.domain.constant.response.code;

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
    public static final InventoryResponseCode INVENTORY_SAVED_FAILED = new InventoryResponseCode(1006, "Failed to save inventory with id %s");
    public static final InventoryResponseCode INVENTORY_TRANSACTION_SAVED_FAILED = new InventoryResponseCode(1007, "Failed to save inventory transaction with id %s");
    public static final InventoryResponseCode PRODUCT_ATTRIBUTE_NOT_EXISTS = new InventoryResponseCode(1008, "Product %s does not have attribute %s");
    public static final InventoryResponseCode NOT_ENOUGH_STOCK = new InventoryResponseCode(1009, "Not enough stock to export for product %s");
    public static final InventoryResponseCode NO_GOODS_BE_EXPORTED = new InventoryResponseCode(1010, "No goods have been exported");
    public static final InventoryResponseCode NO_GOODS_BE_TRANSFERRED = new InventoryResponseCode(1011, "No goods have been transferred");
    public static final InventoryResponseCode NO_INVENTORY_FOR_SOME_PRODUCTS = new InventoryResponseCode(1012, "No inventory for some products");
    public static final InventoryResponseCode CAN_NOT_SAVE_ORDER_OUTBOX_MESSAGE =
            new InventoryResponseCode(1013, "Can not save order outbox message, sagaId: %s");
    public static final InventoryResponseCode FULFILLMENT_SAVED_FAILED =
            new InventoryResponseCode(1014, "Failed to save fulfillment history with id %s");
    public static final InventoryResponseCode FULFILLMENT_HISTORY_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1015, "Fulfillment history is in wrong state for initialization");
    public static final InventoryResponseCode THERE_IS_UNAVAILABLE_PRODUCTS =
            new InventoryResponseCode(1016, "Products with ids: %s is unavailable");
    public static final InventoryResponseCode OUTDATED_EXPORT_INFORMATION =
            new InventoryResponseCode(1017, "Export information is outdated, outdated product information: %s");
    public static final InventoryResponseCode PRODUCT_OUT_OF_STOCK =
            new InventoryResponseCode(1018, "Product with id %s and sku %s is out of stock. Required entity: %s, current stock: %s");
    public static final InventoryResponseCode PRODUCT_FULFILLMENT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new InventoryResponseCode(1019, "Product fulfillment is in wrong state for initialization");
    protected InventoryResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public InventoryResponseCode(String message) {
        this.code = formatErrorCode(GENERAL_CODE);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
