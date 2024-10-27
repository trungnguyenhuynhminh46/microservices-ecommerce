package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.role.GetRolesResponseData;
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
    public ApiResponse<GetRolesResponseData> getRoles() {
        return ApiResponse.<GetRolesResponseData>builder()
                .message("Roles retrieved successfully")
                .data(GetRolesResponseData.builder()
                        .roles(roleRepository.findAll())
                        .build())
                .build();
    }
}
