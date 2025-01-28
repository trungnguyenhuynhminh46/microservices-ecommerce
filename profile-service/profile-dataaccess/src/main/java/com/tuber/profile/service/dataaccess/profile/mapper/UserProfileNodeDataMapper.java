package com.tuber.profile.service.dataaccess.profile.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.profile.service.dataaccess.profile.entity.UserProfileNode;
import com.tuber.profile.service.domain.entity.UserProfile;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class UserProfileNodeDataMapper {
    public abstract UserProfileNode userProfileToUserProfileNode(UserProfile userProfile);
    public abstract UserProfile userProfileNodeToUserProfile(UserProfileNode userProfileNode);
    protected String map(UniqueUUID id) {
        return id.getValue().toString();
    }
    protected UniqueUUID map(String id) {
        return new UniqueUUID(UUID.fromString(id));
    }
}
