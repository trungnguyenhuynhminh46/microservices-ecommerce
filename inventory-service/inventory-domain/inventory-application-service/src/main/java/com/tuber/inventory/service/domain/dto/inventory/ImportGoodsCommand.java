package com.tuber.inventory.service.domain.dto.inventory;

import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportGoodsCommand {
    @NotNull(message = "Goods can not be null")
    @NotEmpty(message = "Goods can not be empty")
    List<GoodInfoDTO> goods;
    String description;
    @NotNull(message = "Warehouse id can not be null")
    UUID warehouseId;
}
