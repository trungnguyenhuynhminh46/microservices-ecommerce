package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IdentityApplicationService identityApplicationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserAccountResponseData>> register(@RequestBody RegisterUserAccountCommand registerUserAccountCommand) {
        ApiResponse<RegisterUserAccountResponseData> registerUserAccountResponse = identityApplicationService.register(registerUserAccountCommand);
        log.info("Successfully registered user with username {}", registerUserAccountCommand.getUsername());
        return ResponseEntity.ok(registerUserAccountResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserAccountResponseData>> login(@RequestBody LoginUserAccountCommand loginUserAccountCommand) {
        ApiResponse<LoginUserAccountResponseData> loginUserAccountResponse = identityApplicationService.login(loginUserAccountCommand);
        log.info("Successfully logged in user with username {}", loginUserAccountCommand.getUsername());
        return ResponseEntity.ok(loginUserAccountResponse);
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectUserAccountResponseData>> introspect(@RequestBody IntrospectUserAccountCommand introspectUserAccountCommand) {
        ApiResponse<IntrospectUserAccountResponseData> introspectUserAccountResponse = identityApplicationService.introspect(introspectUserAccountCommand);
        log.info("Successfully introspected user with refresh token {}", introspectUserAccountCommand.getAccessToken());
        return ResponseEntity.ok(introspectUserAccountResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponseData>> refresh(@RequestBody RefreshTokenCommand refreshTokenCommand) {
        ApiResponse<RefreshTokenResponseData> refreshTokenResponse = identityApplicationService.refresh(refreshTokenCommand);
        log.info("Successfully refreshed user with refresh token {}", refreshTokenCommand.getRefreshToken());
        return ResponseEntity.ok(refreshTokenResponse);
    }
}
