package com.tuber.inventory.service.domain.dto.shared;

import com.tuber.application.validators.ValidUUID;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodInfoDTO {
    @NotNull(message = "Product id is mandatory")
    @ValidUUID
    String  productId;
    @NotNull(message = "Product attributes is mandatory")
    List<AttributeDTO> attributes;
    @NotNull(message = "Product quantity is mandatory")
    @Min(value = 1, message = "The amount of product to be transferred need to be larger than 0")
    Integer quantity;
}
