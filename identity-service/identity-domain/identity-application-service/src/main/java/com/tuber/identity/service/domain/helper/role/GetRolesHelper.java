package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.role.GetRoleResponseData;
import com.tuber.identity.service.domain.dto.role.GetRolesResponseData;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetRolesHelper {
    RoleRepository roleRepository;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    public ApiResponse<GetRolesResponseData> getRoles() {
        return ApiResponse.<GetRolesResponseData>builder()
                .message("Roles retrieved successfully")
                .data(GetRolesResponseData.builder()
                        .roles(roleRepository.findAll())
                        .build())
                .build();
    }

    public ApiResponse<GetRoleResponseData> getRole(String name) {
        return ApiResponse.<GetRoleResponseData>builder()
                .message("Role retrieved successfully")
                .data(GetRoleResponseData.builder()
                        .role(commonIdentityServiceHelper.verifyRoleExist(name))
                        .build())
                .build();
    }

    public ApiResponse<GetRolesResponseData> getRolesByUsername(String username) {
        return ApiResponse.<GetRolesResponseData>builder()
                .message(String.format("Roles for user with username %s retrieved successfully", username))
                .data(GetRolesResponseData.builder()
                        .roles(roleRepository.findByUsername(username))
                        .build())
                .build();
    }
}
