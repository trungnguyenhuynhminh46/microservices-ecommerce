package com.tuber.identity.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.dto.permission.GetPermissionQuery;
import com.tuber.identity.service.domain.dto.permission.GetPermissionResponseData;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.dto.role.*;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.validators.ValidUUID;
import jakarta.validation.Valid;

public interface IdentityApplicationService {
    ApiResponse<CreateUserAccountResponseData> createUserAccount(@Valid CreateUserAccountCommand createUserAccountCommand);
    ApiResponse<GetUserByIdResponseData> getUserByUserId(@ValidUUID GetUserByIdQuery getUserByIdQuery);
    ApiResponse<GetUsersResponseData> getUsers();
    ApiResponse<RegisterUserAccountResponseData> register(@Valid RegisterUserAccountCommand registerUserAccountCommand);
    ApiResponse<LoginUserAccountResponseData> login(@Valid LoginUserAccountCommand loginUserAccountCommand);
    ApiResponse<IntrospectUserAccountResponseData> introspect(@Valid IntrospectUserAccountCommand introspectUserAccountCommand);
    ApiResponse<RefreshTokenResponseData> refresh(@Valid RefreshTokenCommand refreshTokenCommand);
    ApiResponse<LogoutUserAccountResponseData> logout(@Valid LogoutUserAccountCommand logoutUserAccountCommand);
    ApiResponse<GetRolesResponseData> getRoles();
    ApiResponse<GetRoleResponseData> getRole(GetRoleQuery getRoleQuery);
    ApiResponse<AssignRoleToUserResponseData> assignRoleToUser(AssignRoleToUserCommand assignRoleToUserCommand);
    ApiResponse<GetRolesResponseData> getRolesByUsername(String username);
    ApiResponse<CreateRoleResponseData> createRole(@Valid CreateRoleCommand createRoleCommand);
    ApiResponse<DeleteRoleResponseData> deleteRole(DeleteRoleCommand deleteRoleCommand);
    ApiResponse<GetPermissionsResponseData> getPermissions();
    ApiResponse<GetPermissionResponseData> getPermission(GetPermissionQuery getPermissionQuery);
    ApiResponse<GetPermissionsResponseData> getPermissionsByRoleName(String roleName);
    ApiResponse<GetPermissionsResponseData> getPermissionsByUsername(String username);
    ApiResponse<GetPermissionsResponseData> assignPermissionToRole(String roleName, String permissionName);
}
