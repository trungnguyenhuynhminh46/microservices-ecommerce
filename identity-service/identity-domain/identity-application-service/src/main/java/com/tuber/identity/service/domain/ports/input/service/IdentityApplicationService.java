package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand);
}
