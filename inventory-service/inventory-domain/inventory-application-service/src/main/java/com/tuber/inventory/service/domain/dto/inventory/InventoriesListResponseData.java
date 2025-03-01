package com.tuber.inventory.service.domain.dto.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoriesListResponseData {
    List<InventoryResponseData> inventories;
    List<GoodInfoDTO> failedRequests;
    Integer total;
}
