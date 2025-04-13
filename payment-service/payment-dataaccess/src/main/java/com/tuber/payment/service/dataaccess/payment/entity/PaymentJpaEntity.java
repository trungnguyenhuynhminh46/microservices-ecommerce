package com.tuber.payment.service.dataaccess.payment.entity;

import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentJpaEntity {
    @Id
    UUID id;
    UUID orderId;
    UUID customerId;
    BigDecimal totalPrice;
    LocalDate createdAt;
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentJpaEntity that = (PaymentJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
