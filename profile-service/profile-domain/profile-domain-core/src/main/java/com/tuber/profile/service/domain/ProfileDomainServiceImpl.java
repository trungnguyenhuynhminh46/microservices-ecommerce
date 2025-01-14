package com.tuber.profile.service.domain;

import com.tuber.profile.service.domain.entity.UserProfile;

public class ProfileDomainServiceImpl implements ProfileDomainService {
    @Override
    public UserProfile validateAndInitializeUserProfile(UserProfile invalidatedUserProfile) {
        return invalidatedUserProfile.validate().initialize();
    }
}
