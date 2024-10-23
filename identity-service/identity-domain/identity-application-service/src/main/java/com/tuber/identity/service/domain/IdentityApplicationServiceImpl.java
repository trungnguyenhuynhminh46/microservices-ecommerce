package com.tuber.identity.service.domain;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.handler.authentication.RegisterUserAccountHandler;
import com.tuber.identity.service.domain.handler.user.account.CreateUserAccountHandler;
import com.tuber.identity.service.domain.handler.user.account.GetUserByIdHandler;
import com.tuber.identity.service.domain.handler.user.account.GetUsersHandler;
import com.tuber.identity.service.domain.helper.IntrospectUserAccountHelper;
import com.tuber.identity.service.domain.helper.LoginUserAccountHelper;
import com.tuber.identity.service.domain.helper.RefreshTokenHelper;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class IdentityApplicationServiceImpl implements IdentityApplicationService {
    private final CreateUserAccountHandler createUserAccountHandler;
    private final GetUserByIdHandler getUserByIdHandler;
    private final GetUsersHandler getUsersHandler;
    private final RegisterUserAccountHandler registerUserAccountHandler;
    private final LoginUserAccountHelper loginUserAccountHelper;
    private final IntrospectUserAccountHelper introspectUserAccountHelper;
    private final RefreshTokenHelper refreshTokenHelper;
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

    @Override
    public ApiResponse<RegisterUserAccountResponseData> register(RegisterUserAccountCommand registerUserAccountCommand) {
        return registerUserAccountHandler.register(registerUserAccountCommand);
    }

    @Override
    public ApiResponse<LoginUserAccountResponseData> login(LoginUserAccountCommand loginUserAccountCommand) {
        return loginUserAccountHelper.login(loginUserAccountCommand);
    }

    @Override
    public ApiResponse<IntrospectUserAccountResponseData> introspect(IntrospectUserAccountCommand introspectUserAccountCommand) {
        return introspectUserAccountHelper.introspect(introspectUserAccountCommand);
    }

    @Override
    public ApiResponse<RefreshTokenResponseData> refresh(RefreshTokenCommand refreshTokenCommand) {
        return refreshTokenHelper.refresh(refreshTokenCommand);
    }
}
