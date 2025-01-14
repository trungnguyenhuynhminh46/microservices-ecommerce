package com.tuber.profile.service.domain;

import com.tuber.profile.service.domain.entity.UserProfile;

public interface ProfileDomainService {
    UserProfile validateAndInitializeUserProfile(UserProfile invalidatedUserProfile);
}
