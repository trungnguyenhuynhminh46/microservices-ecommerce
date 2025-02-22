package com.tuber.inventory.service.domain.dto.warehouse;

import com.tuber.inventory.service.domain.dto.shared.AddressDTO;
import com.tuber.inventory.service.domain.dto.shared.CoordinateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateWarehouseInfoCommand {
    @NotNull(message = "Warehouse id is mandatory")
    UUID id;
    JsonNullable<@NotNull @NotBlank(message = "Warehouse name is mandatory") String> name;
    JsonNullable<@NotNull(message = "Address is mandatory") AddressDTO> address;
    JsonNullable<@NotNull(message = "Coordinate is mandatory") CoordinateDTO> location;
    JsonNullable<String> description;
}
