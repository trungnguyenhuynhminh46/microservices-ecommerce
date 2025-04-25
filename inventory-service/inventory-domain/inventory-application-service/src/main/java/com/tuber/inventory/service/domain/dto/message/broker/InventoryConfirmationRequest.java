package com.tuber.inventory.service.domain.dto.message.broker;

import com.tuber.domain.valueobject.enums.InventoryOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryConfirmationRequest {
    UUID id;
    UUID sagaId;
    UUID orderId;
    BigDecimal totalPrice;
    BigDecimal finalPrice;
    LocalDate createdAt;
    @Setter
    InventoryOrderStatus inventoryOrderStatus;
    List<ExportInformation> exportInformationList;
}
