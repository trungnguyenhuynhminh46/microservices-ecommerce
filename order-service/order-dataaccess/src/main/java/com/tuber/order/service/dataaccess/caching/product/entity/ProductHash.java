package com.tuber.order.service.dataaccess.caching.product.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("Product")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductHash {
}
