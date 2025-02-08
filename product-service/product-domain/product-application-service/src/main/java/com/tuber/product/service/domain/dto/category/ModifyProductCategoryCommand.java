package com.tuber.product.service.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModifyProductCategoryCommand {
    @NotNull
    @NotBlank(message = "Category code is mandatory")
    String code;
    @NotNull
    @NotBlank(message = "Category name is mandatory")
    String name;
    String description;
}
