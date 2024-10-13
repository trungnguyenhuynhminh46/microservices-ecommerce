package com.tuber.mapper;

import com.tuber.dto.user.account.CreateUserAccountResponseData;
import com.tuber.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDataMapper {
    @Mapping(target = "id", source = "userAccount.getId().getValue()")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    CreateUserAccountResponseData userAccountEntityToCreateUserAccountResponseData(UserAccount userAccount);
}
