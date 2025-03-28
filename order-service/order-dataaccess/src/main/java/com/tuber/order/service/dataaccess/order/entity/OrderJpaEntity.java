package com.tuber.order.service.dataaccess.order.entity;

import com.tuber.order.service.dataaccess.voucher.entity.VoucherJpaEntity;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderJpaEntity {
    @Id
    UUID id;
    String trackingId;
    String buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    Set<OrderItemJpaEntity> orderItems = new HashSet<>();
    @ManyToMany(mappedBy = "ordersUsedVouchers", fetch = FetchType.LAZY)
    Set<VoucherJpaEntity> vouchers = new HashSet<>();
    BigDecimal finalPrice;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderJpaEntity that = (OrderJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
