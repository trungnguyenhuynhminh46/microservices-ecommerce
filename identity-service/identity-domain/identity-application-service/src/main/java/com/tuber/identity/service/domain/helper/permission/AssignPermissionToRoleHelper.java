package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.permission.AlterPermissionCommand;
import com.tuber.identity.service.domain.dto.permission.AlterPermissionsResponseData;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignPermissionToRoleHelper {
    PermissionRepository permissionRepository;

    public ApiResponse<AlterPermissionsResponseData> assignPermissionToRole(AlterPermissionCommand removePermissionCommand) {
        String roleName = removePermissionCommand.getRoleName();
        String permissionName = removePermissionCommand.getPermissionName();
        if (permissionRepository.existsByNameAndRoleName(roleName, permissionName)) {
            throw new IdentityDomainException(IdentityResponseCode.PERMISSION_EXISTED, HttpStatus.BAD_REQUEST.value());
        }
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add(permissionName);

        Set<Permission> newPermissionsSet = permissionRepository.assignPermissionsToRole(roleName, permissionsSet);
        return ApiResponse.<AlterPermissionsResponseData>builder()
                .data(AlterPermissionsResponseData.builder().role(roleName).permissions(newPermissionsSet).build())
                .build();
    }
}
