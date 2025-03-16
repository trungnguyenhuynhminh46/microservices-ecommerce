package com.tuber.inventory.service.domain.dto.http.client.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProductDetailsQuery {
    Set<UUID> productIds;
}
