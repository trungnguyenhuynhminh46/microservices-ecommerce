package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.permission.GetPermissionsResponseData;
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

    public ApiResponse<GetPermissionsResponseData> getPermissions() {
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findAll())
                        .build())
                .build();
    }

    public ApiResponse<GetPermissionsResponseData> getPermissionsByRole(String roleName) {
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findAll())
                        .build())
                .build();
    }

    public ApiResponse<GetPermissionsResponseData> getPermissionsByUsername(String username) {
        return ApiResponse.<GetPermissionsResponseData>builder()
                .message("Permissions retrieved successfully")
                .data(GetPermissionsResponseData.builder()
                        .permissions(permissionRepository.findAll())
                        .build())
                .build();
    }
}
