package com.tuber.profile.service.domain.dto.user.profile;

import com.tuber.application.validators.ValidDob;
import com.tuber.application.validators.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateUserProfileCommand {
    @NotNull
    @NotBlank(message = "UserId is mandatory")
    @ValidUUID
    String userId;

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
