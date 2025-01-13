package com.tuber.profile.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;

public class ProfileApplicationServiceImpl implements ProfileApplicationService{
    @Override
    public ApiResponse<UserProfileResponseData> getProfile(String profileId) {
        return null;
    }

    @Override
    public ApiResponse<UserProfileResponseData> createUserProfile(CreateUserProfileCommand createUserProfileCommand) {
        return null;
    }
}
