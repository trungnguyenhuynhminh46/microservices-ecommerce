package com.tuber.inventory.service.domain.dto.inventory;

import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferGoodsListResponseData {
    List<TransferGoodsResponseData> transferredGoodsInfo;
    List<GoodInfoDTO> failedRequests;
    Integer total;
}
