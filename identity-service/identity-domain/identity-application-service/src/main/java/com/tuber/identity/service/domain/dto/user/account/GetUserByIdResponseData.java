package com.tuber.identity.service.domain.dto.user.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserByIdResponseData {
    UUID id;
    String username;
    String email;
    LocalDate createdAt;
    LocalDate updatedAt;
}
