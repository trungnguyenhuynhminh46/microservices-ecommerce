package com.tuber.payment.service.dataaccess.history.entity;

import com.tuber.payment.service.domain.valueobject.enums.TransactionType;
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
@Table(name = "credit_history")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditHistoryJpaEntity {
    @Id
    UUID id;
    UUID customerId;
    BigDecimal amount;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    LocalDate createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditHistoryJpaEntity that = (CreditHistoryJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
