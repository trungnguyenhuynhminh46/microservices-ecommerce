package com.tuber.profile.service.domain.dto.user.profile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueryUserProfilesResponseData {
    List<UserProfileResponseData> userProfiles;
    int total;
}
