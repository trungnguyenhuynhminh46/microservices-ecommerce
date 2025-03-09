package com.tuber.order.service.domain.valueobject;

import com.tuber.domain.valueobject.id.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
