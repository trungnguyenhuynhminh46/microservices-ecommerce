package com.tuber;

import com.tuber.application.handler.ResponseBase;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.dto.user.account.CreateUserAccountResponseData;
import com.tuber.handler.CreateUserAccountHandler;
import com.tuber.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class IdentityApplicationServiceImpl implements IdentityApplicationService {
    private final CreateUserAccountHandler createUserAccountHandler;
    @Override
    public ResponseBase<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        return createUserAccountHandler.createUserAccount(createUserAccountCommand);
    }
}
