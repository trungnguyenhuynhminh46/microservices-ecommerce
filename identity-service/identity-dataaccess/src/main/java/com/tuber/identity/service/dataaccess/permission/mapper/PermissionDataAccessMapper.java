package com.tuber.identity.service.dataaccess.permission.mapper;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.id.EnumId;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PermissionDataAccessMapper {
    @Mapping(target = "name", source = "id")
    public abstract PermissionJpaEntity permissionEntityToPermissionJpaEntity(Permission permission);

    @Mapping(target = "id", source = "name")
    public abstract Permission permissionJpaEntityToPermissionEntity(PermissionJpaEntity permissionJpaEntity);

    protected UserPermission map(EnumId<UserPermission> id) {
        return id.getValue();
    }

    protected RoleJpaEntity map(Role role) {
        return RoleJpaEntity.builder()
                .name(role.getName())
                .description(role.getDescription())
                .permissions(
                        role.getPermissions().stream()
                                .map(this::permissionEntityToPermissionJpaEntity)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    protected Role map(RoleJpaEntity roleJpaEntity) {
        return Role.builder()
                .id(roleJpaEntity.getName())
                .description(roleJpaEntity.getDescription())
                .permissions(
                        roleJpaEntity.getPermissions().stream()
                                .map(this::permissionJpaEntityToPermissionEntity)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
