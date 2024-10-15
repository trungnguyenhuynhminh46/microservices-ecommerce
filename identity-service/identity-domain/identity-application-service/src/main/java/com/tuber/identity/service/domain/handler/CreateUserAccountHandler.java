package com.tuber.identity.service.domain.handler;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountHandler {
    private final CreateUserAccountHelper createUserAccountHelper;
    private final UserDataMapper userDataMapper;

    public ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(createUserAccountCommand);
        log.info("User account is created with id: {}", userAccountCreatedEvent.getUserAccount().getId().getValue());
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
