package com.tuber.profile.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import com.tuber.profile.service.domain.helper.user.profile.UserProfileHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileApplicationServiceImpl implements ProfileApplicationService{
    UserProfileHelper userProfileHelper;
    @Override
    public ApiResponse<UserProfileResponseData> getProfile(String profileId) {
        return userProfileHelper.getProfile(profileId);
    }

    @Override
    public ApiResponse<UserProfileResponseData> createUserProfile(CreateUserProfileCommand createUserProfileCommand) {
        return userProfileHelper.persistUserProfile(createUserProfileCommand);
    }
}
