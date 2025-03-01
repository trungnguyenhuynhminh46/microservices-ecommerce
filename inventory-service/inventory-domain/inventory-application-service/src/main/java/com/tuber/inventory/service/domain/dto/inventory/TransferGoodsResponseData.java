package com.tuber.inventory.service.domain.dto.inventory;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferGoodsResponseData {
    InventoryResponseData sourceInventory;
    InventoryResponseData destinationInventory;
}
