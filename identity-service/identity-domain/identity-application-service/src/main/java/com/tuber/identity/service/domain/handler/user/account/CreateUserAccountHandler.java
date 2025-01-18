package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.mapper.http.client.ProfileServiceDataMapper;
import com.tuber.identity.service.domain.ports.output.http.client.ProfileServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateUserAccountHandler {
    CreateUserAccountHelper createUserAccountHelper;
    UserDataMapper userDataMapper;
    ProfileServiceClient profileServiceClient;
    ProfileServiceDataMapper profileServiceDataMapper;

    public ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(createUserAccountCommand);

        profileServiceClient.createUserProfile(profileServiceDataMapper.createUserAccountCommandToCreateUserProfileCommand(createUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId()));

        log.info("User account is created with id: {}", userAccountCreatedEvent.getUserAccount().getUserId());
        CreateUserAccountResponseData createUserAccountResponseData = userDataMapper.userAccountEntityToCreateUserAccountResponseData(userAccountCreatedEvent.getUserAccount());
        // Persist to outbox table if needed

        log.info("Returning CreateUserAccountResponseData with user account id: {}", userAccountCreatedEvent.getUserAccount().getId());
        return ApiResponse.<CreateUserAccountResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message("User account created successfully")
                .data(createUserAccountResponseData)
                .build();
    }
}
