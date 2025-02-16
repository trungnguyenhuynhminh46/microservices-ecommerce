package com.tuber.product.service.domain.dto.template.product;

import com.tuber.product.service.domain.dto.shared.TemplateAttributeDTO;
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
public class CreateTemplateProductCommand {
    @NotNull
    @NotBlank(message = "Product name is mandatory")
    String name;
    @NotNull(message = "Price is mandatory")
    BigDecimal price;
    String description;
    String tags;
    UUID categoryId;
    List<TemplateAttributeDTO> attributes;
}
