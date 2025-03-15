package com.tuber.profile.service.dataaccess.adapter;

import com.tuber.profile.service.dataaccess.mapper.UserProfileNodeDataMapper;
import com.tuber.profile.service.dataaccess.repository.UserProfileNeo4jRepository;
import com.tuber.profile.service.domain.entity.UserProfile;
import com.tuber.profile.service.domain.ports.output.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
    public Optional<UserProfile> findById(String profileId) {
        return userProfileNeo4jRepository.findById(profileId)
                .map(userProfileNodeDataMapper::userProfileNodeToUserProfile);
    }

    @Override
    public Optional<UserProfile> findByUserId(String userId) {
        return userProfileNeo4jRepository.findByUserId(userId)
                .map(userProfileNodeDataMapper::userProfileNodeToUserProfile);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileNeo4jRepository.findAll()
                .stream()
                .map(userProfileNodeDataMapper::userProfileNodeToUserProfile)
                .toList();
    }
}
