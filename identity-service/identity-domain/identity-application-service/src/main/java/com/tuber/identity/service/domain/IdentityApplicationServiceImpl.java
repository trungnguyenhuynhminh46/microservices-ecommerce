package com.tuber.identity.service.domain;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.handler.CreateUserAccountHandler;
import com.tuber.identity.service.domain.handler.GetUserByIdHandler;
import com.tuber.identity.service.domain.handler.GetUsersHandler;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdentityApplicationServiceImpl implements IdentityApplicationService {
    private final CreateUserAccountHandler createUserAccountHandler;
    private final GetUserByIdHandler getUserByIdHandler;
    private final GetUsersHandler getUsersHandler;
    @Override
    public ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        return createUserAccountHandler.createUserAccount(createUserAccountCommand);
    }

    @Override
    public ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery) {
        return getUserByIdHandler.getUserByUserId(getUserByIdQuery);
    }

    @Override
    public ApiResponse<GetUsersResponseData> getUsers() {
        return getUsersHandler.getUsers();
    }
}
