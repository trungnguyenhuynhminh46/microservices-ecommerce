package com.tuber.profile.service.domain.ports.output.repository;

import com.tuber.profile.service.domain.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository {
    UserProfile save(UserProfile userProfile);
    Optional<UserProfile> findById(String userId);
}
