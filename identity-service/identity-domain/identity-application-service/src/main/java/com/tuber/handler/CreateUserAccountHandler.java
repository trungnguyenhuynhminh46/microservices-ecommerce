package com.tuber.handler;

import com.tuber.application.handler.ResponseBase;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.dto.user.account.CreateUserAccountResponseData;
import com.tuber.event.UserAccountCreatedEvent;
import com.tuber.helper.CreateUserAccountHelper;
import com.tuber.mapper.UserDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountHandler {
    private final CreateUserAccountHelper createUserAccountHelper;
    private final UserDataMapper userDataMapper;

    public ResponseBase<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(createUserAccountCommand);
        log.info("User account is created with id: {}", userAccountCreatedEvent.getUserAccount().getId().getValue());
        CreateUserAccountResponseData createUserAccountResponseData = userDataMapper.userAccountEntityToCreateUserAccountResponseData(userAccountCreatedEvent.getUserAccount());

        // Persist to outbox table if needed

        log.info("Returning CreateUserAccountResponseData with user account id: {}", userAccountCreatedEvent.getUserAccount().getId());

        return ResponseBase.<CreateUserAccountResponseData>createResponse(
                "123",
                "User account created successfully",
                createUserAccountResponseData
        );
    }
}
