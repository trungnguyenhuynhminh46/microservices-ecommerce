package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.role.*;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserCommand;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserResponseData;
import com.tuber.identity.service.domain.dto.user.account.RemoveRoleFromUserCommand;
import com.tuber.identity.service.domain.dto.user.account.RemoveRoleFromUserResponseData;
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
@RequestMapping(value = "/${service.name}/roles", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class RoleController {
    private final IdentityApplicationService identityApplicationService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_ROLES')")
    public ResponseEntity<ApiResponse<GetRolesResponseData>> getRoles() {
        ApiResponse<GetRolesResponseData> getRolesResponseData = identityApplicationService.getRoles();
        log.info("Returning all roles");
        return ResponseEntity.ok(getRolesResponseData);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('GET_ROLES')")
    public ResponseEntity<ApiResponse<GetRoleResponseData>> getRole(@PathVariable("name") String name) {
        ApiResponse<GetRoleResponseData> getRoleResponseData = identityApplicationService.getRole(GetRoleQuery.builder().name(name).build());
        log.info("Returning role with name: {}", name);
        return ResponseEntity.ok(getRoleResponseData);
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("hasAuthority('GET_ROLES')")
    public ResponseEntity<ApiResponse<GetRolesResponseData>> getRolesByUsername(@PathVariable("username") String username) {
        ApiResponse<GetRolesResponseData> getRolesResponseData = identityApplicationService.getRolesByUsername(username);
        log.info("Returning roles for user with username: {}", username);
        return ResponseEntity.ok(getRolesResponseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<ApiResponse<CreateRoleResponseData>> createRole(@RequestBody CreateRoleCommand createRoleCommand) {
        ApiResponse<CreateRoleResponseData> createRoleResponseData = identityApplicationService.createRole(createRoleCommand);
        log.info("Role created with name: {}", createRoleCommand.getName());
        return ResponseEntity.ok(createRoleResponseData);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ResponseEntity<ApiResponse<DeleteRoleResponseData>> deleteRole(@PathVariable("name") String name) {
        ApiResponse<DeleteRoleResponseData> deleteRoleResponseData = identityApplicationService.deleteRole(DeleteRoleCommand.builder().name(name).build());
        log.info("Role deleted with name: {}", name);
        return ResponseEntity.ok(deleteRoleResponseData);
    }

    @PostMapping("/{roleName}/users/{username}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ResponseEntity<ApiResponse<AssignRoleToUserResponseData>> assignRoleToUser(@PathVariable("username") String username, @PathVariable("roleName") String roleName) {
        AssignRoleToUserCommand assignRoleToUserCommand = AssignRoleToUserCommand.builder().username(username).roleName(roleName).build();
        log.info("Assigning role {} to user with username: {}", roleName, username);
        ApiResponse<AssignRoleToUserResponseData> assignRoleToUserResponseData = identityApplicationService.assignRoleToUser(assignRoleToUserCommand);
        log.info("Role {} assigned to user with username: {}", roleName, username);
        return ResponseEntity.ok(assignRoleToUserResponseData);
    }

    @DeleteMapping("/{roleName}/users/{username}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ResponseEntity<ApiResponse<RemoveRoleFromUserResponseData>> removeRoleFromUser(@PathVariable("username") String username, @PathVariable("roleName") String roleName) {
        RemoveRoleFromUserCommand removeRoleFromUserCommand = RemoveRoleFromUserCommand.builder().username(username).roleName(roleName).build();
        log.info("Removing role {} from user with username: {}", roleName, username);
        ApiResponse<RemoveRoleFromUserResponseData> removeRoleFromUserResponseData = identityApplicationService.removeRoleFromUser(removeRoleFromUserCommand);
        log.info("Role {} removed from user with username: {}", roleName, username);
        return ResponseEntity.ok(removeRoleFromUserResponseData);
    }
}
