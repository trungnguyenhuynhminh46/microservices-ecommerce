package com.tuber.product.service.domain.dto.template.product;

import com.tuber.product.service.domain.dto.shared.TemplateAttributeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModifyTemplateProductCommand {
    @NotNull(message = "Product id is mandatory")
    UUID id;
    @NotNull
    @NotBlank(message = "Product name is mandatory")
    String name;
    @NotNull(message = "Price is mandatory")
    BigDecimal price;
    JsonNullable<String> description;
    JsonNullable<String> tags;
    JsonNullable<UUID> categoryId;
    JsonNullable<Set<UUID>> attributeIds;
}
