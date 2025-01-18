package com.tuber.identity.service.domain.dto.authentication;

import com.tuber.application.validators.ValidDob;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterUserAccountCommand {
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

    @NotNull
    @NotBlank(message = "FirstName is mandatory")
    @Size(min = 2, max = 50, message = "FirstName should be between 2 and 50 characters")
    String firstName;

    @NotNull
    @NotBlank(message = "LastName is mandatory")
    @Size(min = 2, max = 50, message = "LastName should be between 2 and 50 characters")
    String lastName;

    @NotNull
    @ValidDob
    LocalDate dob;

    @NotNull
    @NotBlank(message = "City name is mandatory")
    String city;
}
