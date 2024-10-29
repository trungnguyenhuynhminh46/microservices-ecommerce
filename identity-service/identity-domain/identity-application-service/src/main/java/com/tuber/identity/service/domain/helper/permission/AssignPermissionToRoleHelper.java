package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignPermissionToRoleHelper {
    PermissionRepository permissionRepository;
    public ApiResponse<GetPermissionsResponseData> assignPermissionToRole(String roleName, String permissionName) {
        Set<UserPermission> permissionsSet = new HashSet<>();
        permissionsSet.add(UserPermission.valueOf(permissionName));

        Set<Permission> newPermissionsSet = permissionRepository.assignPermissionsToRole(roleName, permissionsSet);
        return ApiResponse.<GetPermissionsResponseData>builder()
                .data(GetPermissionsResponseData.builder().permissions(newPermissionsSet).build())
                .build();
    }
}
