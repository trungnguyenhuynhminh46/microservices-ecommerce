package com.tuber.inventory.service.dataaccess.caching.product.entity;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@RedisHash("product")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductHash {
    @Id
    UUID id;
    String name;
    BigDecimal price;
    List<ProductAttributeHash> attributes;
}
