package com.tuber.profile.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.QueryUserProfilesResponseData;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import jakarta.validation.Valid;

public interface ProfileApplicationService {
    ApiResponse<UserProfileResponseData> getProfile(String profileId);
    ApiResponse<UserProfileResponseData> createUserProfile(@Valid CreateUserProfileCommand createUserProfileCommand);
    ApiResponse<QueryUserProfilesResponseData> getAllUserProfiles();
}
