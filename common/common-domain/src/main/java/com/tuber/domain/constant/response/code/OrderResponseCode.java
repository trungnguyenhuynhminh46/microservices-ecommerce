package com.tuber.domain.constant.response.code;

public class OrderResponseCode extends ResponseCodeBase {
    protected final Integer GENERAL_CODE = -1;
    protected final String serviceName = "order";
    public static final OrderResponseCode SUCCESS_RESPONSE = new OrderResponseCode(1000, "Order is created successfully");
    public static final OrderResponseCode PRODUCT_UNAVAILABLE = new OrderResponseCode(1001, "Some products are unavailable or have been out of stock");
    public static final OrderResponseCode ORDER_SAVE_FAILED = new OrderResponseCode(1002, "Failed to save order");
    public static final OrderResponseCode INVALID_VOUCHER = new OrderResponseCode(1003, "One or more vouchers are invalid or inactive");
    public static final OrderResponseCode ORDER_IN_WRONG_STATE_FOR_INITIALIZATION = new OrderResponseCode(1004, "Order is in wrong state for initialization");
    public static final OrderResponseCode ORDER_ITEM_IN_WRONG_STATE_FOR_INITIALIZATION = new OrderResponseCode(1005, "Order item is in wrong state for initialization");
    public static final OrderResponseCode PRODUCT_IN_ORDER_ITEM_IS_OUTDATED = new OrderResponseCode(1006, "Product in order item is outdated");
    public static final OrderResponseCode FAILED_TO_SAVE_PAYMENT_OUTBOX = new OrderResponseCode(1007, "Could not save OrderPaymentOutboxMessage with outbox id: %s");

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
