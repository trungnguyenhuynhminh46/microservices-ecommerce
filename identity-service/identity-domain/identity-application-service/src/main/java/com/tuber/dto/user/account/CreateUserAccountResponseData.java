package com.tuber.dto.user.account;

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
public class CreateUserAccountResponseData {
    String id;
    String username;
    String email;
    LocalDate createdAt;
    LocalDate updatedAt;
}
