package com.tuber.inventory.service.domain.dto.inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryResponseData {
    UUID id;
    UUID productId;
    String sku;
    UUID warehouseId;
    Integer quantity;
    LocalDate createdAt;
    LocalDate updatedAt;
}
