package com.tuber.inventory.service.dataaccess.fulfillment.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFulfillmentJpaEntityId implements Serializable {
    Long id;
    FulfillmentHistoryJpaEntity fulfillmentHistory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFulfillmentJpaEntityId that = (ProductFulfillmentJpaEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(fulfillmentHistory, that.fulfillmentHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fulfillmentHistory);
    }
}
