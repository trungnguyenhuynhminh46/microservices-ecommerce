package com.tuber.identity.service.domain.mapper;

import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDataMapper {
    @Mapping(target = "id", expression = "java(userAccount.getId().getValue())")
    CreateUserAccountResponseData userAccountEntityToCreateUserAccountResponseData(UserAccount userAccount);

    @Mapping(target = "passwordEncoded", constant = "false")
    UserAccount createUserAccountCommandToUserAccount(CreateUserAccountCommand createUserAccountCommand);
}
