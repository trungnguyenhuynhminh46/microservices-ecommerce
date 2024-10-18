package com.tuber.identity.service.domain.handler;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.GetUsersResponseData;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetUsersHandler {
    private final UserDataMapper userDataMapper;
    private final UserAccountRepository userAccountRepository;
    public ApiResponse<GetUsersResponseData> getUsers() {
        return ApiResponse.<GetUsersResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message("User accounts found")
                .data(userDataMapper.userAccountEntitiesToGetUsersResponseData(userAccountRepository.findAll()))
                .build();
    }
}
