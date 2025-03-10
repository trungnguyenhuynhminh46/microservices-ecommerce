package com.tuber.order.service.domain.dto.shared;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemDTO {
    @NotNull(message = "Product ID must not be null")
    UUID productId;
    String sku;
    @NotNull(message = "Product quantity must not be null")
    @Min(value = 1, message = "Product quantity must be greater than 0")
    Integer quantity;
}
