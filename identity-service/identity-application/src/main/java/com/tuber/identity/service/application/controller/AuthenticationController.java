package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountResponseData;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/auth", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IdentityApplicationService identityApplicationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserAccountResponseData>> login(@RequestBody @Valid LoginUserAccountCommand loginUserAccountCommand) {
        ApiResponse<LoginUserAccountResponseData> loginUserAccountResponse = identityApplicationService.login(loginUserAccountCommand);
        log.info("Successfully logged in user with username {}", loginUserAccountCommand.getUsername());
        return ResponseEntity.ok(loginUserAccountResponse);
    }
}
