package com.tuber.inventory.service.domain.dto.inventory;

import com.tuber.application.validators.ValidUUID;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferGoodsCommand {
    @NotNull(message = "Goods can not be null")
    @NotEmpty(message = "Goods can not be empty")
    List<GoodInfoDTO> goods;

    String description;

    @NotNull(message = "Source warehouse id can not be null")
    @ValidUUID
    String sourceWarehouseId;

    @NotNull(message = "Destination warehouse id can not be null")
    @ValidUUID
    String destinationWarehouseId;

}
