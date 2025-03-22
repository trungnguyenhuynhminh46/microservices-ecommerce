package com.tuber.order.service.domain.dto.http.client.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalInventoryDetailsResponseData {
    Set<InternalInventoryDetailResponseData> inventoryDetails;
    boolean hasUnavailableProducts;
}
