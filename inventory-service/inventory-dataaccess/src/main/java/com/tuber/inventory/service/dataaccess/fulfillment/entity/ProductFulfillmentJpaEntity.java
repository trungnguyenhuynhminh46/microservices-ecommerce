package com.tuber.inventory.service.dataaccess.fulfillment.entity;

import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductFulfillmentJpaEntityId.class)
@Table(name = "product_fulfillment")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFulfillmentJpaEntity {
    @Id
    Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fulfillment_history_id")
    FulfillmentHistoryJpaEntity fulfillmentHistory;
    UUID orderId;
    UUID productId;
    UUID inventoryId;
    String sku;
    BigDecimal basePrice;
    Integer quantity;
    @Enumerated(EnumType.STRING)
    ProductFulfillStatus fulfillStatus;
}
