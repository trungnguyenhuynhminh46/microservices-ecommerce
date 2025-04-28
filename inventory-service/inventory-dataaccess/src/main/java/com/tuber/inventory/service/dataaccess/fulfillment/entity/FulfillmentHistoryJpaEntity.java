package com.tuber.inventory.service.dataaccess.fulfillment.entity;

import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
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
@Table(name = "fulfillment_history")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FulfillmentHistoryJpaEntity {
    @Id
    UUID id;
    String trackingId;
    UUID orderId;
    BigDecimal totalPrice;
    @OneToMany(mappedBy = "fulfillmentHistory", cascade = CascadeType.ALL)
    Set<ProductFulfillmentJpaEntity> productFulfillments = new HashSet<>();
    @Enumerated(EnumType.STRING)
    OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FulfillmentHistoryJpaEntity that = (FulfillmentHistoryJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
