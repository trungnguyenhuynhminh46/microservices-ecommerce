package com.tuber.identity.service.domain.mapper;

import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUsersResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserDataMapper {
    @Mapping(target = "id", expression = "java(userAccount.getId().getValue())")
    public abstract CreateUserAccountResponseData userAccountEntityToCreateUserAccountResponseData(UserAccount userAccount);

    @Mapping(target = "passwordEncoded", constant = "false")
    public abstract UserAccount createUserAccountCommandToUserAccount(CreateUserAccountCommand createUserAccountCommand);

    @Mapping(target = "id", expression = "java(userAccount.getId().getValue())")
    public abstract GetUserByIdResponseData userAccountEntityToGetUserByIdResponseData(UserAccount userAccount);

    public GetUsersResponseData userAccountEntitiesToGetUsersResponseData(List<UserAccount> allUsers) {
        return GetUsersResponseData.builder()
                .users(allUsers.stream().map(this::userAccountEntityToGetUserByIdResponseData).toList())
                .build();
    }

    public abstract CreateUserAccountCommand registerUserAccountCommandToCreateUserAccountCommand(RegisterUserAccountCommand registerUserAccountCommand);
}
