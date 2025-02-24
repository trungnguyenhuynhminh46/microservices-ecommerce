package com.tuber.inventory.service.domain.dto.shared;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeDTO {
    @NotNull(message = "Product attribute name is mandatory")
    String name;
    @NotNull(message = "Product attribute value is mandatory")
    String value;
}
