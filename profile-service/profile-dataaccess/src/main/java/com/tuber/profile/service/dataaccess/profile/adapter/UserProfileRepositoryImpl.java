package com.tuber.profile.service.dataaccess.profile.adapter;

import com.tuber.profile.service.dataaccess.profile.mapper.UserProfileNodeDataMapper;
import com.tuber.profile.service.dataaccess.profile.repository.UserProfileNeo4jRepository;
import com.tuber.profile.service.domain.entity.UserProfile;
import com.tuber.profile.service.domain.ports.output.repository.UserProfileRepository;

import java.util.Optional;

public class UserProfileRepositoryImpl implements UserProfileRepository {
    UserProfileNeo4jRepository userProfileNeo4jRepository;
    UserProfileNodeDataMapper userProfileNodeDataMapper;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileNodeDataMapper.userProfileNodeToUserProfile(
                userProfileNeo4jRepository.save(
                        userProfileNodeDataMapper.userProfileToUserProfileNode(userProfile)
                )
        );
    }

    @Override
    public Optional<UserProfile> findById(String userId) {
        return userProfileNeo4jRepository.findById(userId)
                .map(userProfileNodeDataMapper::userProfileNodeToUserProfile);
    }
}
