package com.tuber.product.service.domain.dto.product;

import com.tuber.application.dto.product.ProductAttributeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductCommand {
    @NotNull(message = "Product name can not be null")
    @NotBlank(message = "Product name is mandatory")
    String name;
    BigDecimal price;
    String description;
    String tags;
    UUID categoryId;
    List<ProductAttributeDTO> attributes;
}
