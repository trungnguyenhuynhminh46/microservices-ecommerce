package com.tuber.profile.service.domain.dto.user.profile;

import com.tuber.application.validators.ValidDob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    String userId;
    @NotNull
    @NotBlank(message = "FirstName is mandatory")
    String firstName;
    @NotNull
    @NotBlank(message = "LastName is mandatory")
    String lastName;
    @NotNull
    @ValidDob
    LocalDate dob;
    @NotNull
    @NotBlank(message = "City name is mandatory")
    String city;
}
