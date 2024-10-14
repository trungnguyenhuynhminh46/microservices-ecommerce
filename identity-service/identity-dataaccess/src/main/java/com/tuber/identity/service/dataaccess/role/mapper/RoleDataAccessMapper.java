package com.tuber.identity.service.dataaccess.role.mapper;

import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleDataAccessMapper {
    @Mapping(target = "name", expression = "java(role.getId().getValue())" )
    @Mapping(target = "permissions", ignore = true)
    RoleJpaEntity roleEntityToRoleJpaEntity(Role role);
}
