package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.role.GetRoleResponseData;
import com.tuber.identity.service.domain.dto.role.GetRolesResponseData;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.exception.RoleNotFoundException;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetRolesHelper {
    RoleRepository roleRepository;

    public ApiResponse<GetRolesResponseData> getRoles() {
        return ApiResponse.<GetRolesResponseData>builder()
                .message("Roles retrieved successfully")
                .data(GetRolesResponseData.builder()
                        .roles(roleRepository.findAll())
                        .build())
                .build();
    }

    private Role verifyRoleExists(UserRole name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            throw new RoleNotFoundException(IdentityResponseCode.ROLE_NOT_EXISTS, HttpStatus.NOT_FOUND.value());
        }
        return role.get();
    }

    public ApiResponse<GetRoleResponseData> getRole(UserRole name) {
        return ApiResponse.<GetRoleResponseData>builder()
                .message("Role retrieved successfully")
                .data(GetRoleResponseData.builder()
                        .role(verifyRoleExists(name))
                        .build())
                .build();
    }
}
