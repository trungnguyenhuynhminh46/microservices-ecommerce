package com.tuber.identity.service.dataaccess.role.mapper;

import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.domain.valueobject.id.EnumId;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoleDataAccessMapper {
    @Mapping(target = "name", source = "id")
    public abstract RoleJpaEntity roleEntityToRoleJpaEntity(Role role);

    @Mapping(target = "id", source = "name")
    public abstract Role roleJpaEntityToRoleEntity(RoleJpaEntity roleJpaEntity);

    protected UserRole map(EnumId<UserRole> id) {
        return id.getValue();
    }

    protected Permission map(PermissionJpaEntity permissionJpaEntity) {
        return Permission.builder()
                .id(permissionJpaEntity.getName())
                .description(permissionJpaEntity.getDescription())
                .roles(
                        permissionJpaEntity.getRoles().stream()
                                .map(this::roleJpaEntityToRoleEntity)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    protected PermissionJpaEntity map(Permission permission) {
        return PermissionJpaEntity.builder()
                .name(permission.getId().getValue())
                .description(permission.getDescription())
                .roles(
                        permission.getRoles().stream()
                                .map(this::roleEntityToRoleJpaEntity)
                                .collect(Collectors.toSet())
                )
                .build();

    }
}
