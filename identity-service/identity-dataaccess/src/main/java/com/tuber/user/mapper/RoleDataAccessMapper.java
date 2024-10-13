package com.tuber.user.mapper;

import com.tuber.entity.Role;
import com.tuber.user.entity.RoleJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleDataAccessMapper {
    @Mapping(target = "name", source = "role.getId().getValue()" )
    @Mapping(target = "permissions", ignore = true)
    RoleJpaEntity roleEntityToRoleJpaEntity(Role role);
}
