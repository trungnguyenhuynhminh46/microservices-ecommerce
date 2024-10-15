package com.tuber.identity.service.domain;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.handler.CreateUserAccountHandler;
import com.tuber.identity.service.domain.handler.GetUserByIdHandler;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
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
    private final GetUserByIdHandler getUserByIdHandler;
    @Override
    public ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        return createUserAccountHandler.createUserAccount(createUserAccountCommand);
    }

    @Override
    public ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery) {
        return getUserByIdHandler.getUserByUserId(getUserByIdQuery);
    }
}
