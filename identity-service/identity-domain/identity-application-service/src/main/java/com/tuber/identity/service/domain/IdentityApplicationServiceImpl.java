package com.tuber.identity.service.domain;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.*;
import com.tuber.identity.service.domain.dto.permission.GetPermissionQuery;
import com.tuber.identity.service.domain.dto.permission.GetPermissionResponseData;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.dto.role.*;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.handler.authentication.RegisterUserAccountHandler;
import com.tuber.identity.service.domain.handler.user.account.CreateUserAccountHandler;
import com.tuber.identity.service.domain.handler.user.account.GetUserByIdHandler;
import com.tuber.identity.service.domain.handler.user.account.GetUsersHandler;
import com.tuber.identity.service.domain.helper.authentication.IntrospectUserAccountHelper;
import com.tuber.identity.service.domain.helper.authentication.LoginUserAccountHelper;
import com.tuber.identity.service.domain.helper.authentication.LogoutUserAccountHelper;
import com.tuber.identity.service.domain.helper.authentication.RefreshTokenHelper;
import com.tuber.identity.service.domain.helper.permission.GetPermissionsHelper;
import com.tuber.identity.service.domain.helper.role.CreateRoleHelper;
import com.tuber.identity.service.domain.helper.role.DeleteRoleHelper;
import com.tuber.identity.service.domain.helper.role.GetRolesHelper;
import com.tuber.identity.service.domain.helper.user.account.AssignRoleToUserHelper;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityApplicationServiceImpl implements IdentityApplicationService {
    CreateUserAccountHandler createUserAccountHandler;
    GetUserByIdHandler getUserByIdHandler;
    GetUsersHandler getUsersHandler;
    RegisterUserAccountHandler registerUserAccountHandler;
    LoginUserAccountHelper loginUserAccountHelper;
    IntrospectUserAccountHelper introspectUserAccountHelper;
    RefreshTokenHelper refreshTokenHelper;
    LogoutUserAccountHelper logoutUserAccountHelper;
    GetRolesHelper getRolesHelper;
    AssignRoleToUserHelper assignRoleToUserHelper;
    CreateRoleHelper createRoleHelper;
    DeleteRoleHelper deleteRoleHelper;
    GetPermissionsHelper getPermissionsHelper;

    @Override
    public ApiResponse<CreateUserAccountResponseData> createUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        return createUserAccountHandler.createUserAccount(createUserAccountCommand);
    }

    @Override
    public ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery) {
        return getUserByIdHandler.getUserByUserId(getUserByIdQuery);
    }

    @Override
    public ApiResponse<GetUsersResponseData> getUsers() {
        return getUsersHandler.getUsers();
    }

    @Override
    public ApiResponse<RegisterUserAccountResponseData> register(RegisterUserAccountCommand registerUserAccountCommand) {
        return registerUserAccountHandler.register(registerUserAccountCommand);
    }

    @Override
    public ApiResponse<LoginUserAccountResponseData> login(LoginUserAccountCommand loginUserAccountCommand) {
        return loginUserAccountHelper.login(loginUserAccountCommand);
    }

    @Override
    public ApiResponse<IntrospectUserAccountResponseData> introspect(IntrospectUserAccountCommand introspectUserAccountCommand) {
        return introspectUserAccountHelper.introspect(introspectUserAccountCommand);
    }

    @Override
    public ApiResponse<RefreshTokenResponseData> refresh(RefreshTokenCommand refreshTokenCommand) {
        return refreshTokenHelper.refresh(refreshTokenCommand);
    }

    @Override
    public ApiResponse<LogoutUserAccountResponseData> logout(LogoutUserAccountCommand logoutUserAccountCommand) {
        return logoutUserAccountHelper.logout(logoutUserAccountCommand);
    }

    @Override
    public ApiResponse<GetRolesResponseData> getRoles() {
        return getRolesHelper.getRoles();
    }

    @Override
    public ApiResponse<GetRoleResponseData> getRole(GetRoleQuery getRoleQuery) {
        return getRolesHelper.getRole(getRoleQuery.getName());
    }

    @Override
    public ApiResponse<AssignRoleToUserResponseData> assignRoleToUser(AssignRoleToUserCommand assignRoleToUserCommand) {
        return assignRoleToUserHelper.assignRoleToUser(assignRoleToUserCommand);
    }

    @Override
    public ApiResponse<GetRolesResponseData> getRolesByUsername(String username) {
        return getRolesHelper.getRolesByUsername(username);
    }

    @Override
    public ApiResponse<CreateRoleResponseData> createRole(CreateRoleCommand createRoleCommand) {
        return createRoleHelper.createRole(createRoleCommand);
    }

    @Override
    public ApiResponse<DeleteRoleResponseData> deleteRole(DeleteRoleCommand deleteRoleCommand) {
        return deleteRoleHelper.deleteRole(deleteRoleCommand);
    }

    @Override
    public ApiResponse<GetPermissionsResponseData> getPermissions() {
        return getPermissionsHelper.getPermissions();
    }

    @Override
    public ApiResponse<GetPermissionResponseData> getPermission(GetPermissionQuery getPermissionQuery) {
        return getPermissionsHelper.getPermission(getPermissionQuery.getName());
    }
}
