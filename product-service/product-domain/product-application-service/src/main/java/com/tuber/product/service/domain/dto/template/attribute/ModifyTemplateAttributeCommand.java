package com.tuber.product.service.domain.dto.template.attribute;

import com.tuber.product.service.domain.dto.shared.ProductAttributeOption;
import jakarta.validation.constraints.NotBlank;
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
public class ModifyTemplateAttributeCommand {
    @NotNull(message = "Template attribute id is mandatory")
    UUID id;
    @NotNull
    @NotBlank(message = "Template attribute name is mandatory")
    String name;
    String description;
    @NotNull(message = "You must add at least one option to the attribute")
    List<ProductAttributeOption> options;
}
