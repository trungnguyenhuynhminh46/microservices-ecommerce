package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.domain.dto.permission.GetPermissionQuery;
import com.tuber.identity.service.domain.dto.permission.GetPermissionResponseData;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/permissions", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class PermissionController {
    private final IdentityApplicationService identityApplicationService;

    @GetMapping
    public ResponseEntity<ApiResponse<GetPermissionsResponseData>> getPermissions() {
        ApiResponse<GetPermissionsResponseData> getPermissionsResponseData = identityApplicationService.getPermissions();
        log.info("Returning all permissions");
        return ResponseEntity.ok(getPermissionsResponseData);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<GetPermissionResponseData>> getPermission(@PathVariable("name") String name) {
        GetPermissionQuery getPermissionQuery = GetPermissionQuery.builder().name(UserPermission.valueOf(name)).build();
        ApiResponse<GetPermissionResponseData> getPermissionResponseData = identityApplicationService.getPermission(getPermissionQuery);
        log.info("Returning permission with name: {}", name);
        return ResponseEntity.ok(getPermissionResponseData);
    }
    
    // Get Permissions By Rolename
    // Create Permission
}
