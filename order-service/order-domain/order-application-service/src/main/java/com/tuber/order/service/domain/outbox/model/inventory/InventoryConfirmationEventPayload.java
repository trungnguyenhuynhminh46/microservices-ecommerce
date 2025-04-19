package com.tuber.order.service.domain.outbox.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryConfirmationEventPayload {
    @JsonProperty
    UUID orderId;
    @JsonProperty
    UUID inventoryId;
    @JsonProperty
    BigDecimal price;
    @JsonProperty
    LocalDate createdAt;
    @JsonProperty
    String inventoryOrderStatus;
    @JsonProperty
    private List<ProductExportInformationDTO> productExportInformationDTOList;
}
