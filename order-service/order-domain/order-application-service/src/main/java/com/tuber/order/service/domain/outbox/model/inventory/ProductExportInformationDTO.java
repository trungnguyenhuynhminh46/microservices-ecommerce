package com.tuber.order.service.domain.outbox.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductExportInformationDTO {
    @JsonProperty
    UUID productId;
    @JsonProperty
    String sku;
    @JsonProperty
    BigDecimal basePrice;
    @JsonProperty
    Integer requiredQuantity;
}
