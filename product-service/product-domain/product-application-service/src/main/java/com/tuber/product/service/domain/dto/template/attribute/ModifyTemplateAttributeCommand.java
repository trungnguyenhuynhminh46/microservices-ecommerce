package com.tuber.product.service.domain.dto.template.attribute;

import com.tuber.product.service.domain.dto.shared.ProductAttributeOption;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModifyTemplateAttributeCommand {
    @NotNull(message = "Template attribute id is mandatory")
    UUID id;
    String name;
    JsonNullable<String> defaultValue;
    JsonNullable<List<ProductAttributeOption>> options;
}
