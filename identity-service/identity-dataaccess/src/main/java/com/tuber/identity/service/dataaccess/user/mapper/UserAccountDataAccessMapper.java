package com.tuber.identity.service.dataaccess.user.mapper;

import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserAccountDataAccessMapper {
    @Mapping(target = "id", expression = "java(userAccount.getId().getValue())")
    @Mapping(target = "roles", ignore = true)
    public abstract UserAccountJpaEntity userAccountEntityToUserAccountJpaEntity(UserAccount userAccount);

    @Mapping(target = "id", expression = "java(userAccountJpaEntity.getId())")
    @Mapping(target = "passwordEncoded", constant = "true")
    public abstract UserAccount userAccountJpaEntityToUserAccountEntity(UserAccountJpaEntity userAccountJpaEntity);

    protected Role map(RoleJpaEntity roleJpaEntity) {
        return Role.builder()
                .id(roleJpaEntity.getName())
                .description(roleJpaEntity.getDescription())
                .build();
    }

}
