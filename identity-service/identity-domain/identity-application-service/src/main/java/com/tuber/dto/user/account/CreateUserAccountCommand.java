package com.tuber.dto.user.account;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateUserAccountCommand {
    @NotNull
    String username;
    @NotNull
    String email;
    @NotNull
    String password;
}
