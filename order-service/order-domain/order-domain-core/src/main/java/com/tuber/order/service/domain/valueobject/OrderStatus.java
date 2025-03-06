package com.tuber.order.service.domain.valueobject;

public enum OrderStatus {
    INITIALIZED, PROCESSING, APPROVED,
    CANCELLING, CANCELLED,
    DELIVERING, DELIVERED, REJECTED, PAID
}
