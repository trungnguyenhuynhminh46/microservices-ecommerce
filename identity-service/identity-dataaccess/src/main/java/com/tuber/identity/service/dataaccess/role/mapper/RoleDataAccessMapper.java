package com.tuber.identity.service.dataaccess.role.mapper;

import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.domain.valueobject.id.EnumId;
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
    @Mapping(target = "permissions", ignore = true)
    public abstract Role roleJpaEntityToRoleEntity(RoleJpaEntity roleJpaEntity);

    protected UserRole map(EnumId<UserRole> id) {
        return id.getValue();
    }
}
