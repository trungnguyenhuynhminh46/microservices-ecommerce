package com.tuber.product.service.domain.dto.product;

import com.tuber.application.dto.product.ProductAttributeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModifyProductCommand {
    @NotNull(message = "Product id is mandatory")
    UUID id;
    JsonNullable<
            @NotNull(message = "Product name can not be null")
            @NotBlank(message = "Product name is mandatory")
                    String> name;
    JsonNullable<BigDecimal> price;
    JsonNullable<String> description;
    JsonNullable<String> tags;
    JsonNullable<UUID> categoryId;
    JsonNullable<List<ProductAttributeDTO>> attributes;
}
