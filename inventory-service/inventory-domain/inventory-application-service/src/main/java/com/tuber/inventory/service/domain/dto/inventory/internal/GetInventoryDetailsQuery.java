package com.tuber.inventory.service.domain.dto.inventory.internal;

import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetInventoryDetailsQuery {
    Set<ProductIdWithSkuDTO> productIds;
}
