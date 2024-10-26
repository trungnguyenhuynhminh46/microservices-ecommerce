package com.tuber.identity.service.dataaccess.permission.mapper;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.id.EnumId;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PermissionDataAccessMapper {
    @Mapping(target = "name", source = "id")
    public abstract PermissionJpaEntity permissionEntityToPermissionJpaEntity(Permission permission);

    @Mapping(target = "id", source = "name")
    public abstract Permission permissionJpaEntityToPermissionEntity(PermissionJpaEntity permissionJpaEntity);

    protected EnumId<UserPermission> map(UserPermission name) {
        return new EnumId<UserPermission>(name);
    }

    protected UserPermission map(EnumId<UserPermission> id) {
        return id.getValue();
    }
}
