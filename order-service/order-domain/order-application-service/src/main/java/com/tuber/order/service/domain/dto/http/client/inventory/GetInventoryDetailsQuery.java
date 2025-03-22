package com.tuber.order.service.domain.dto.http.client.inventory;

import com.tuber.order.service.domain.dto.shared.ProductIdWithSkuDTO;
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
