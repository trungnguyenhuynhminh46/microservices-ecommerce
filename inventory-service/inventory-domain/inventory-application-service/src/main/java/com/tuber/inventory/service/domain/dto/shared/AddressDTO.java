package com.tuber.inventory.service.domain.dto.shared;

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
public class AddressDTO {
    @NotNull
    @NotBlank(message = "Warehouse street is mandatory")
    String street;
    @NotNull
    @NotBlank(message = "Postal code is mandatory")
    String postalCode;
    @NotNull
    @NotBlank(message = "City is mandatory")
    String city;
}
