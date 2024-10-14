package com.tuber.user.mapper;

import com.tuber.entity.UserAccount;
import com.tuber.user.entity.UserAccountJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAccountDataAccessMapper {
    @Mapping(target = "id", source = "userAccount.getId().getValue()")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "passwordEncoded", ignore = true)
    UserAccountJpaEntity userAccountEntityToUserAccountJpaEntity(UserAccount userAccount);

    @Mapping(target = "id", source = "userAccountJpaEntity.getId()")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "passwordEncoded", source = "true")
    UserAccount userAccountJpaEntityToUserAccountEntity(UserAccountJpaEntity userAccountJpaEntity);
}
