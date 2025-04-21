package com.tuber.domain.constant.response.code;

public class OrderResponseCode extends ResponseCodeBase {
    protected final String serviceName = "order";
    public static final OrderResponseCode SUCCESS_RESPONSE = new OrderResponseCode(1000, "Order is created successfully");
    public static final OrderResponseCode PRODUCT_UNAVAILABLE = new OrderResponseCode(1001, "Some products are unavailable or have been out of stock");
    public static final OrderResponseCode ORDER_SAVE_FAILED = new OrderResponseCode(1002, "Failed to save order");
    public static final OrderResponseCode INVALID_VOUCHER = new OrderResponseCode(1003, "One or more vouchers are invalid or inactive");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_FOR_INITIALIZATION = new OrderResponseCode(1004, "Order is in wrong state for initialization");
    public static final OrderResponseCode ORDER_ITEM_IN_WRONG_STATE_FOR_INITIALIZATION = new OrderResponseCode(1005, "Order item is in wrong state for initialization");
    public static final OrderResponseCode PRODUCT_IN_ORDER_ITEM_IS_OUTDATED = new OrderResponseCode(1006, "Product in order item is outdated");
    public static final OrderResponseCode FAILED_TO_SAVE_PAYMENT_OUTBOX = new OrderResponseCode(1007, "Could not save OrderPaymentOutboxMessage of order with orderId: %s");
    public static final OrderResponseCode ORDER_NOT_FOUND = new OrderResponseCode(1008, "Could not find order with order id: %s");
    public static final OrderResponseCode PAYMENT_OUTBOX_MESSAGE_OF_SAGA_NOT_FOUND = new OrderResponseCode(1009, "Payment outbox message with saga id: %s and saga status: %s could not be found!");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_FOR_PAYMENT = new OrderResponseCode(1010, "Order with id: %s is not in proper state for payment");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_FOR_INIT_CANCELLING = new OrderResponseCode(1011, "Order with id: %s is not in proper state for init canceling");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_FOR_CANCELING_PAYMENT = new OrderResponseCode(1012, "Order with id: %s is not in proper state for canceling payment");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_TO_BE_READY_FOR_FULFILLMENT = new OrderResponseCode(1013, "Order with id: %s is not in proper state to be ready for fulfillment");
    public static final OrderResponseCode FAILED_TO_SAVE_INVENTORY_OUTBOX = new OrderResponseCode(1014, "Could not save InventoryOutboxMessage: orderId: %s, inventoryId: %s");
    public static final ResponseCodeBase INVENTORY_CONFIRMATION_OUTBOX_MESSAGE_NOT_FOUND = new OrderResponseCode(1015, "Inventory confirmation outbox message with saga id: %s could not be found!");
    protected OrderResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public OrderResponseCode(String message) {
        this.code = formatErrorCode(GENERAL_CODE);
        this.message = message;
    }

    @Override
    public String getServiceName() {
        return this.serviceName;
    }

    @Override
    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
