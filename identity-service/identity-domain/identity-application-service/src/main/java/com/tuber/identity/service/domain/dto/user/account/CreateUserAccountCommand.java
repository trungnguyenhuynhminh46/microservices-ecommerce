package com.tuber.identity.service.domain.dto.user.account;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateUserAccountCommand {
    @NotNull
    @NotBlank(message = "Username is mandatory")
    @Size(min = 6, message = "Username must be at least 6 characters long")
    String username;
    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Size(min = 6, message = "Email must be at least 6 characters long")
    @Email(message = "Invalid email format")
    String email;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter and one lowercase letter")
    private String password;
}
