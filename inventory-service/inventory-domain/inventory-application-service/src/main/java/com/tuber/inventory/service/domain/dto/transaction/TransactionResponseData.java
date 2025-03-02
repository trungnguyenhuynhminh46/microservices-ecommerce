package com.tuber.inventory.service.domain.dto.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionResponseData {
    UUID id;
    UUID productId;
    String sku;
    UUID srcWarehouseId;
    UUID destWarehouseId;
    Integer quantity;
    String creator;
    String transactionType;
    String description;
    String createdDate;
}
