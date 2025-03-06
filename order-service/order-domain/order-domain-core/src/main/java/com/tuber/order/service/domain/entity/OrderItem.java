package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.UUID;

public class OrderItem extends BaseEntity<UniqueUUID> {
    UUID orderId;
    Integer quantity;
    Money price;
}
