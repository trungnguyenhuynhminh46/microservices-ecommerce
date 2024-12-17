package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.permission.*;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePermissionResponseData>> createPermission(@RequestBody CreatePermissionCommand createPermissionCommand) {
        ApiResponse<CreatePermissionResponseData> createPermissionResponseData = identityApplicationService.createPermission(createPermissionCommand);
        log.info("Created permission with name: {}", createPermissionCommand.getName());
        return ResponseEntity.ok(createPermissionResponseData);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<GetPermissionResponseData>> getPermission(@PathVariable("name") String name) {
        GetPermissionQuery getPermissionQuery = GetPermissionQuery.builder().name(name).build();
        ApiResponse<GetPermissionResponseData> getPermissionResponseData = identityApplicationService.getPermission(getPermissionQuery);
        log.info("Returning permission with name: {}", name);
        return ResponseEntity.ok(getPermissionResponseData);
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<ApiResponse<GetPermissionsResponseData>> getPermissionsByRoleName(@PathVariable("roleName") String roleName) {
        ApiResponse<GetPermissionsResponseData> getPermissionsResponseData = identityApplicationService.getPermissionsByRoleName(roleName);
        log.info("Returning permissions for role with name: {}", roleName);
        return ResponseEntity.ok(getPermissionsResponseData);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<ApiResponse<GetPermissionsResponseData>> getPermissionsByUsername(@PathVariable("username") String username) {
        ApiResponse<GetPermissionsResponseData> getPermissionsResponseData = identityApplicationService.getPermissionsByUsername(username);
        log.info("Returning permissions for user with username: {}", username);
        return ResponseEntity.ok(getPermissionsResponseData);
    }

    @PostMapping("/{permissionName}/roles/{roleName}")
    public ResponseEntity<ApiResponse<AlterPermissionsResponseData>> assignPermissionToRole(@PathVariable("roleName") String roleName, @PathVariable("permissionName") String permissionName) {
        AlterPermissionCommand assignPermissionCommand = AlterPermissionCommand.builder().roleName(roleName).permissionName(permissionName).build();
        ApiResponse<AlterPermissionsResponseData> getPermissionsResponseData = identityApplicationService.assignPermissionToRole(assignPermissionCommand);
        log.info("Added permission with name: {} to role with name: {}", permissionName, roleName);
        return ResponseEntity.ok(getPermissionsResponseData);
    }

    @DeleteMapping("/{permissionName}/roles/{roleName}")
    public ResponseEntity<ApiResponse<AlterPermissionsResponseData>> removePermissionFromRole(@PathVariable("roleName") String roleName, @PathVariable("permissionName") String permissionName) {
        AlterPermissionCommand removePermissionCommand = AlterPermissionCommand.builder().roleName(roleName).permissionName(permissionName).build();
        ApiResponse<AlterPermissionsResponseData> getPermissionsResponseData = identityApplicationService.removePermissionFromRole(removePermissionCommand);
        log.info("Removed permission with name: {} from role with name: {}", permissionName, roleName);
        return ResponseEntity.ok(getPermissionsResponseData);
    }
}
