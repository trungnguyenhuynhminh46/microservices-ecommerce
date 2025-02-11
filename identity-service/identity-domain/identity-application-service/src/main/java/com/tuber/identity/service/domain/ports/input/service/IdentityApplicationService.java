package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.dto.permission.*;
import com.tuber.identity.service.domain.dto.role.*;
import com.tuber.identity.service.domain.dto.user.account.*;
import jakarta.validation.Valid;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(@Valid CreateUserAccountCommand createUserAccountCommand);
    ApiResponse<GetUserByIdResponseData> getUserByUserId(@Valid GetUserByIdQuery getUserByIdQuery);
    ApiResponse<GetUsersResponseData> getUsers();
    ApiResponse<RegisterUserAccountResponseData> register(@Valid RegisterUserAccountCommand registerUserAccountCommand);
    ApiResponse<LoginUserAccountResponseData> login(@Valid LoginUserAccountCommand loginUserAccountCommand);
    ApiResponse<IntrospectUserAccountResponseData> introspect(@Valid IntrospectUserAccountCommand introspectUserAccountCommand);
    ApiResponse<RefreshTokenResponseData> refresh(@Valid RefreshTokenCommand refreshTokenCommand);
    ApiResponse<LogoutUserAccountResponseData> logout(@Valid LogoutUserAccountCommand logoutUserAccountCommand);
    ApiResponse<GetRolesResponseData> getRoles();
    ApiResponse<GetRoleResponseData> getRole(@Valid GetRoleQuery getRoleQuery);
    ApiResponse<AssignRoleToUserResponseData> assignRoleToUser(@Valid AssignRoleToUserCommand assignRoleToUserCommand);
    ApiResponse<GetRolesResponseData> getRolesByUsername(String username);
    ApiResponse<CreateRoleResponseData> createRole(@Valid CreateRoleCommand createRoleCommand);
    ApiResponse<DeleteRoleResponseData> deleteRole(@Valid DeleteRoleCommand deleteRoleCommand);
    ApiResponse<GetPermissionsResponseData> getPermissions();
    ApiResponse<GetPermissionResponseData> getPermission(@Valid GetPermissionQuery getPermissionQuery);
    ApiResponse<GetPermissionsResponseData> getPermissionsByRoleName(String roleName);
    ApiResponse<GetPermissionsResponseData> getPermissionsByUsername(String username);
    ApiResponse<AlterPermissionsResponseData> assignPermissionToRole(@Valid AlterPermissionCommand assignPermissionCommand);
    ApiResponse<AlterPermissionsResponseData> removePermissionFromRole(@Valid AlterPermissionCommand removePermissionCommand);
    ApiResponse<RemoveRoleFromUserResponseData> removeRoleFromUser(@Valid RemoveRoleFromUserCommand removeRoleFromUserCommand);
}
