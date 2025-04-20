package com.tuber.order.service.domain.dto.shared;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemDetailDTO {
    Long id;
    UUID orderId;
    UUID productId;
    UUID inventoryId;
    String sku;
    String name;
    UUID warehouseId;
    Integer quantity;
    BigDecimal subTotal;
}
