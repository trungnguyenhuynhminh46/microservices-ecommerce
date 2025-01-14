package com.tuber.profile.service.domain.helper.user.profile;

import com.tuber.application.handler.ApiResponse;
import com.tuber.profile.service.domain.ProfileDomainService;
import com.tuber.profile.service.domain.constant.ProfileResponseCode;
import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import com.tuber.profile.service.domain.entity.UserProfile;
import com.tuber.profile.service.domain.exception.ProfileDomainException;
import com.tuber.profile.service.domain.helper.CommonHelper;
import com.tuber.profile.service.domain.mapper.UserProfileDataMapper;
import com.tuber.profile.service.domain.ports.output.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileHelper {
    UserProfileDataMapper userProfileDataMapper;
    CommonHelper commonHelper;
    ProfileDomainService profileDomainService;
    UserProfileRepository userProfileRepository;

    public ApiResponse<UserProfileResponseData> getProfile(String profileId) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(profileId);
        if (userProfile.isEmpty()) {
            log.warn("User profile with profile id: {} not found!", profileId);
            throw new ProfileDomainException(ProfileResponseCode.USER_PROFILE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), profileId);
        }
        return ApiResponse.<UserProfileResponseData>builder()
                .data(userProfileDataMapper.userProfileToUserProfileResponseData(userProfile.get()))
                .build();
    }

    public ApiResponse<UserProfileResponseData> persistUserProfile(CreateUserProfileCommand createUserProfileCommand) {
        UserProfile invalidatedUserProfile = userProfileDataMapper.createUserProfileCommandToUserProfile(createUserProfileCommand);
        commonHelper.checkCityExists(invalidatedUserProfile.getCity());
        UserProfile userProfile = profileDomainService.validateAndInitializeUserProfile(invalidatedUserProfile);
        return ApiResponse.<UserProfileResponseData>builder()
                .data(userProfileDataMapper.userProfileToUserProfileResponseData(saveUserProfile(userProfile)))
                .build();
    }

    protected UserProfile saveUserProfile(UserProfile userProfile) {
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        if (savedUserProfile == null) {
            log.warn("Could not save user profile!");
            throw new ProfileDomainException(ProfileResponseCode.USER_PROFILE_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), userProfile.getUserId());
        }
        return userProfileRepository.save(userProfile);
    }
}
