package com.tuber.identity.service.domain.dto.http.client.profile;

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
public class UserProfileResponseData {
    String id;
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
