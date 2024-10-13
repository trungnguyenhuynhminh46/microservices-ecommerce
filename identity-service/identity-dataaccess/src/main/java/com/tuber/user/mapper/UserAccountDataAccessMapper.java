package com.tuber.user.mapper;

import com.tuber.entity.UserAccount;
import com.tuber.user.entity.UserAccountJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAccountDataAccessMapper {
    @Mapping(target = "id", source = "userAccount.getId().getValue()")
    @Mapping(target = "roles", ignore = true)
    UserAccountJpaEntity userAccountEntityToUserAccountJpaEntity(UserAccount userAccount);

    @Mapping(target = "id", expression = "java(new com.tuber.domain.valueobject.id.UserAccountId(userAccountJpaEntity.getId()))")
    @Mapping(target = "roles", ignore = true)
    UserAccount userAccountJpaEntityToUserAccountEntity(UserAccountJpaEntity userAccountJpaEntity);
}
