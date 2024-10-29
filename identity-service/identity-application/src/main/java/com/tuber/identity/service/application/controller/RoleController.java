package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.role.*;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<GetRoleResponseData>> getRole(@PathVariable("name") String name) {
        ApiResponse<GetRoleResponseData> getRoleResponseData = identityApplicationService.getRole(GetRoleQuery.builder().name(name).build());
        log.info("Returning role with name: {}", name);
        return ResponseEntity.ok(getRoleResponseData);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<ApiResponse<GetRolesResponseData>> getRolesByUsername(@PathVariable("username") String username) {
        ApiResponse<GetRolesResponseData> getRolesResponseData = identityApplicationService.getRolesByUsername(username);
        log.info("Returning roles for user with username: {}", username);
        return ResponseEntity.ok(getRolesResponseData);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateRoleResponseData>> createRole(@RequestBody CreateRoleCommand createRoleCommand) {
        ApiResponse<CreateRoleResponseData> createRoleResponseData = identityApplicationService.createRole(createRoleCommand);
        log.info("Role created with name: {}", createRoleCommand.getName());
        return ResponseEntity.ok(createRoleResponseData);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse<DeleteRoleResponseData>> deleteRole(@PathVariable("name") String name) {
        ApiResponse<DeleteRoleResponseData> deleteRoleResponseData = identityApplicationService.deleteRole(DeleteRoleCommand.builder().name(name).build());
        log.info("Role deleted with name: {}", name);
        return ResponseEntity.ok(deleteRoleResponseData);
    }
}
