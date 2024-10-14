package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ResponseBase;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;

public interface IdentityApplicationService {
    ResponseBase<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand);
}
