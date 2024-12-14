package com.tuber.identity.service.dataaccess.permission.mapper;

import com.tuber.domain.valueobject.id.UniqueStringId;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PermissionDataAccessMapper {
    @Mapping(target = "name", source = "id")
    @Mapping(target = "roles", ignore = true)
    public abstract PermissionJpaEntity permissionEntityToPermissionJpaEntity(Permission permission);

    @Mapping(target = "id", source = "name")
    public abstract Permission permissionJpaEntityToPermissionEntity(PermissionJpaEntity permissionJpaEntity);

    protected String map(UniqueStringId id) {
        return id.getValue();
    }
}
