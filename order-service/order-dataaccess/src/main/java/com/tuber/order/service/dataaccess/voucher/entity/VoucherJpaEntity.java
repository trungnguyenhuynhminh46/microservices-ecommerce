package com.tuber.order.service.dataaccess.voucher.entity;

import com.tuber.order.service.dataaccess.order.entity.OrderJpaEntity;
import com.tuber.order.service.domain.valueobject.enums.DiscountType;
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
@Table(name = "voucher")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherJpaEntity {
    @Id
    UUID id;
    String code;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_voucher",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    Set<OrderJpaEntity> ordersUsedVouchers = new HashSet<>();
    DiscountType discountType;
    BigDecimal discountAmount;
    BigDecimal discountPercentage;
    BigDecimal minimumOrderAmount;
    BigDecimal maximumDiscountAmount;
    LocalDate expiryDate;
    Integer remain;
    Boolean active;
    LocalDate createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherJpaEntity that = (VoucherJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
