package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.*;
import jakarta.validation.Valid;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand);
    ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery);
    ApiResponse<GetUsersResponseData> getUsers();
}
