package com.tuber.identity.service.domain.mapper.http.client;

import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountCommand;
import com.tuber.identity.service.domain.dto.http.client.profile.CreateUserProfileCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProfileServiceDataMapper {
    public CreateUserProfileCommand createUserAccountCommandToCreateUserProfileCommand(CreateUserAccountCommand createUserAccountCommand, String userId) {
        return CreateUserProfileCommand.builder()
                .userId(userId)
                .firstName(createUserAccountCommand.getFirstName())
                .lastName(createUserAccountCommand.getLastName())
                .dob(createUserAccountCommand.getDob())
                .city(createUserAccountCommand.getCity())
                .build();
    }

    public CreateUserProfileCommand registerUserAccountCommandToCreateUserProfileCommand(RegisterUserAccountCommand registerUserAccountCommand, String userId) {
        return CreateUserProfileCommand.builder()
                .userId(userId)
                .firstName(registerUserAccountCommand.getFirstName())
                .lastName(registerUserAccountCommand.getLastName())
                .dob(registerUserAccountCommand.getDob())
                .city(registerUserAccountCommand.getCity())
                .build();
    }
}
