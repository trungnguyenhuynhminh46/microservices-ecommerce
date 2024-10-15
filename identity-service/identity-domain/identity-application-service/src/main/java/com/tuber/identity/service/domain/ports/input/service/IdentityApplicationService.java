package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import jakarta.validation.Valid;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(@Valid CreateUserAccountCommand createUserAccountCommand);
    ApiResponse<GetUserByIdResponseData> getUserByUserId(@Valid GetUserByIdQuery getUserByIdQuery);
}
