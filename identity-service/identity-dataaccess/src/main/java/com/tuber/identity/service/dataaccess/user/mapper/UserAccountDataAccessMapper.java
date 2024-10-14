package com.tuber.identity.service.dataaccess.user.mapper;

import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountDataAccessMapper {
    @Mapping(target = "id", expression = "java(userAccount.getId().getValue())")
    @Mapping(target = "roles", ignore = true)
    UserAccountJpaEntity userAccountEntityToUserAccountJpaEntity(UserAccount userAccount);

    @Mapping(target = "id", expression = "java(userAccountJpaEntity.getId())")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "passwordEncoded", constant = "true")
    UserAccount userAccountJpaEntityToUserAccountEntity(UserAccountJpaEntity userAccountJpaEntity);
}
