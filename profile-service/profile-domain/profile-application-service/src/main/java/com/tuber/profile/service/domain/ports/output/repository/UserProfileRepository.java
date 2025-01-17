package com.tuber.profile.service.domain.ports.output.repository;

import com.tuber.profile.service.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository {
    UserProfile save(UserProfile userProfile);
    Optional<UserProfile> findById(String profileId);
    Optional<UserProfile> findByUserId(String userId);
}
