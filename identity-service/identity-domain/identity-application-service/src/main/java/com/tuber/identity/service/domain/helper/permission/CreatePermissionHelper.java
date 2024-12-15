package com.tuber.identity.service.domain.helper.permission;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.permission.CreatePermissionCommand;
import com.tuber.identity.service.domain.dto.permission.CreatePermissionResponseData;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreatePermissionHelper {
    PermissionRepository permissionRepository;

    private Permission verifyPermissionNotExists(String permissionName, String description) {
        if (permissionRepository.findByName(permissionName).isPresent()){
            String message = String.format("Permission with name %s existed", permissionName);
            log.warn(message);
            throw new IdentityDomainException(IdentityResponseCode.PERMISSION_EXISTED, HttpStatus.FOUND.value());
        }
        return Permission.builder()
                .id(permissionName)
                .description(description)
                .build();
    }

    public ApiResponse<CreatePermissionResponseData> createPermission(CreatePermissionCommand createPermissionCommand) {
        Permission permission = verifyPermissionNotExists(createPermissionCommand.getName(), createPermissionCommand.getDescription());
        Permission savedPermission = permissionRepository.save(permission);
        return ApiResponse.<CreatePermissionResponseData>builder()
                .message(String.format("Permission with name %s is successfully created", savedPermission.getName()))
                .data(CreatePermissionResponseData.builder()
                        .name(savedPermission.getName())
                        .description(savedPermission.getDescription())
                        .build())
                .build();
    }
}
