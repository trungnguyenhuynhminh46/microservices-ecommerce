package com.tuber.product.service.domain.dto.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModifyProductCategoryCommand {
    String code;
    String name;
    String description;
}
