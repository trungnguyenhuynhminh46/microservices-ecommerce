package com.tuber.inventory.service.dataaccess.caching.product.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeHash {
    Long id;
    String name;
    String defaultValue;
    String options;
}
