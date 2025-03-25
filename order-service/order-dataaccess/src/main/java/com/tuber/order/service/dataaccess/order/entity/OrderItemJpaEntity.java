package com.tuber.order.service.dataaccess.order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderItemJpaEntityId.class)
@Table(name = "order_item")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemJpaEntity {
    @Id
    Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    OrderJpaEntity order;
    UUID productId;
    String sku;
    UUID warehouseId;
    Integer quantity;
    BigDecimal subTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemJpaEntity that = (OrderItemJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }
}
