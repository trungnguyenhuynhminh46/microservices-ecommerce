package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.role.GetRolesResponseData;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/roles", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class RoleController {
    private final IdentityApplicationService identityApplicationService;
    @GetMapping
    public ResponseEntity<ApiResponse<GetRolesResponseData>> getRoles() {
        ApiResponse<GetRolesResponseData> getRolesResponseData = identityApplicationService.getRoles();
        log.info("Returning all roles");
        return ResponseEntity.ok(getRolesResponseData);
    }
    // Get Role
    // Get Roles By User Id
    // Create Role
    // Delete Role
}
