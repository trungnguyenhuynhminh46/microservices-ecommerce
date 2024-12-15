package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.permission.AlterPermissionCommand;
import com.tuber.identity.service.domain.dto.permission.AlterPermissionsResponseData;
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
public class RemovePermissionFromRoleHelper {
    PermissionRepository permissionRepository;
    public ApiResponse<AlterPermissionsResponseData> removePermissionFromRole(AlterPermissionCommand removePermissionCommand) {
        String roleName = removePermissionCommand.getRoleName();
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add(removePermissionCommand.getPermissionName());

        Set<Permission> newPermissionsSet = permissionRepository.removePermissionsFromRole(roleName, permissionsSet);
        return ApiResponse.<AlterPermissionsResponseData>builder()
                .data(AlterPermissionsResponseData.builder().role(roleName).permissions(newPermissionsSet).build())
                .build();
    }
}
