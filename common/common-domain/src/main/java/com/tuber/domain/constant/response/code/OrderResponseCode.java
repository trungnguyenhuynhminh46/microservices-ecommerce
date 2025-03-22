package com.tuber.domain.constant.response.code;

public class OrderResponseCode extends ResponseCodeBase {
    protected final String serviceName = "order";
    public static final OrderResponseCode SUCCESS_RESPONSE = new OrderResponseCode(1000, "Order is created successfully");

    public static final OrderResponseCode PRODUCT_UNAVAILABLE = new OrderResponseCode(1001, "Some products are unavailable or have been out of stock");
    protected OrderResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
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
