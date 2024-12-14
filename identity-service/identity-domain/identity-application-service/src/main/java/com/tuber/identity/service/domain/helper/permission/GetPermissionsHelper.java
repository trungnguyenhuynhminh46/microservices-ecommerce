package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.permission.GetPermissionResponseData;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPermissionsHelper {
    PermissionRepository permissionRepository;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    public ApiResponse<GetPermissionsResponseData> getPermissions() {
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findAll())
                        .build())
                .build();
    }

    public ApiResponse<GetPermissionResponseData> getPermission(String name) {
        Permission savedPermission = commonIdentityServiceHelper.verifyPermissionExists(name);
        return ApiResponse.<GetPermissionResponseData>builder()
                .message(String.format("Permission %s retrieved successfully", name))
                .data(GetPermissionResponseData.builder()
                        .permission(savedPermission)
                        .build())
                .build();
    }

    public ApiResponse<GetPermissionsResponseData> getPermissionsByRole(String roleName) {
        commonIdentityServiceHelper.verifyRoleExist(roleName);
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findByRoleName(roleName))
                        .build())
                .build();
    }

    public ApiResponse<GetPermissionsResponseData> getPermissionsByUsername(String username) {
        commonIdentityServiceHelper.verifyUserAccountWithUsernameExist(username);
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findByUsername(username))
                        .build())
                .build();
    }
}
