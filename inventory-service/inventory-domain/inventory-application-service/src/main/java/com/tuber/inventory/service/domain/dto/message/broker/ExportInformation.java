package com.tuber.inventory.service.domain.dto.message.broker;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportInformation {
    UUID inventoryId;
    UUID productId;
    String sku;
    BigDecimal basePrice;
    int requiredQuantity;
}
