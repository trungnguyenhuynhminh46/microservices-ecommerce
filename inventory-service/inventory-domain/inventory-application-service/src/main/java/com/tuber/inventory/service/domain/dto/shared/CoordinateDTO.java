package com.tuber.inventory.service.domain.dto.shared;

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
public class CoordinateDTO {
    @NotNull(message = "Latitude is mandatory")
    Double latitude;
    @NotNull(message = "Longitude is mandatory")
    Double longitude;
}
