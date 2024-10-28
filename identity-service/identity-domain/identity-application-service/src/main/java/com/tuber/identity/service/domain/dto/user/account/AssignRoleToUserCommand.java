package com.tuber.identity.service.domain.dto.user.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignRoleToUserCommand {
    @NotNull
    @NotBlank(message = "Username is mandatory")
    @Size(min = 6, message = "Username must be at least 6 characters long")
    String username;

    @NotNull
    @NotBlank(message = "Username is mandatory")
    String roleName;
}
