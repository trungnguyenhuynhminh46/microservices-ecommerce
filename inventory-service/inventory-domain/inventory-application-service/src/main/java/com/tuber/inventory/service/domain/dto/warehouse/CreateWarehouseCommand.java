package com.tuber.inventory.service.domain.dto.warehouse;

import com.tuber.inventory.service.domain.dto.shared.AddressDTO;
import com.tuber.inventory.service.domain.dto.shared.CoordinateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateWarehouseCommand {
    @NotNull
    @NotBlank(message = "Warehouse name is mandatory")
    String name;
    @NotNull(message = "Address is mandatory")
    AddressDTO address;
    @NotNull(message = "Location is mandatory")
    CoordinateDTO location;
    String description;
}
