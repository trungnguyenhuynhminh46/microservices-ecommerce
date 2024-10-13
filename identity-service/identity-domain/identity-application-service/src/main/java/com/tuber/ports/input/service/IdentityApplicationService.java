package com.tuber.ports.input.service;

import com.tuber.application.handler.ResponseBase;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.dto.user.account.CreateUserAccountResponseData;

public interface IdentityApplicationService {
    ResponseBase<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand);
}
