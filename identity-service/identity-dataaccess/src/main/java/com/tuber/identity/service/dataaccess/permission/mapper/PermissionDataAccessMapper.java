package com.tuber.identity.service.dataaccess.permission.mapper;

import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionDataAccessMapper {
    @Mapping(target="name", expression = "java(permission.getId().getValue())")
    PermissionJpaEntity permissionEntityToPermissionJpaEntity(Permission permission);
}
