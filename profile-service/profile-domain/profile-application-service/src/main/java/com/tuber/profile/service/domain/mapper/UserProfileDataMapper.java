package com.tuber.profile.service.domain.mapper;

import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import com.tuber.profile.service.domain.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserProfileDataMapper {
    @Mapping(target = "id", ignore = true)
    public abstract UserProfile createUserProfileCommandToUserProfile(CreateUserProfileCommand createUserProfileCommand);

    @Mapping(target = "id", expression = "java(userProfile.getId().getValue().toString())")
    public abstract UserProfileResponseData userProfileToUserProfileResponseData(UserProfile userProfile);
}
