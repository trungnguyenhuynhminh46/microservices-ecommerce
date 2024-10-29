package com.tuber.identity.service.dataaccess.role.mapper;

import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RoleDataAccessMapper {
    @Mapping(target = "name", source = "id")
    @Mapping(target = "permissions", ignore = true)
    public abstract RoleJpaEntity roleEntityToRoleJpaEntity(Role role);

    @Mapping(target = "id", source = "name")
    public abstract Role roleJpaEntityToRoleEntity(RoleJpaEntity roleJpaEntity);

    protected Permission map(PermissionJpaEntity permissionJpaEntity) {
        return Permission.builder()
                .id(permissionJpaEntity.getName())
                .description(permissionJpaEntity.getDescription())
                .build();
    }
}
