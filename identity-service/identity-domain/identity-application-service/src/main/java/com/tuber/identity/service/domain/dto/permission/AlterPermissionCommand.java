package com.tuber.identity.service.domain.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlterPermissionCommand {
    @NotNull
    @NotBlank(message = "Role name is mandatory")
    String roleName;

    @NotNull
    @NotBlank(message = "Role name is mandatory")
    String permissionName;
}
