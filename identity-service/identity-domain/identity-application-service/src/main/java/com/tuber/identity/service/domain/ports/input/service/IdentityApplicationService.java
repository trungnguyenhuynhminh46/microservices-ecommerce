package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.validators.ValidUUID;
import jakarta.validation.Valid;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(@Valid CreateUserAccountCommand createUserAccountCommand);
    ApiResponse<GetUserByIdResponseData> getUserByUserId(@ValidUUID GetUserByIdQuery getUserByIdQuery);
    ApiResponse<GetUsersResponseData> getUsers();
    ApiResponse<RegisterUserAccountResponseData> register(@Valid RegisterUserAccountCommand registerUserAccountCommand);
    ApiResponse<LoginUserAccountResponseData> login(@Valid LoginUserAccountCommand loginUserAccountCommand);
    ApiResponse<IntrospectUserAccountResponseData> introspect(@Valid IntrospectUserAccountCommand introspectUserAccountCommand);
}
