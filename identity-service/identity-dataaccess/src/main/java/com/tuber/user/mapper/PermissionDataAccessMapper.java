package com.tuber.user.mapper;

import com.tuber.entity.Permission;
import com.tuber.user.entity.PermissionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PermissionDataAccessMapper {
    @Mapping(target="name", source = "permission.getId().getValue()")
    PermissionJpaEntity permissionEntityToPermissionJpaEntity(Permission permission);
}
