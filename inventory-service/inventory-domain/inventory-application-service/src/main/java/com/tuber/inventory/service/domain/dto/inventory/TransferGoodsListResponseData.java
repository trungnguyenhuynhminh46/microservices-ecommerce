package com.tuber.inventory.service.domain.dto.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferGoodsListResponseData {
    List<TransferGoodsResponseData> transferredGoodsInfo;
    List<GoodInfoDTO> failedRequests;
    Integer total;
}
