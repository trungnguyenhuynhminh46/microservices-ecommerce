package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.role.CreateRoleCommand;
import com.tuber.identity.service.domain.dto.role.CreateRoleResponseData;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
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
public class CreateRoleHelper {
    RoleRepository roleRepository;

    private Role verifyRoleNotExists(String roleName, String description) {
        if (roleRepository.findByName(roleName).isPresent()) {
            String message = String.format("Role with name %s existed", roleName);
            log.warn(message);
            throw new IdentityDomainException(IdentityResponseCode.ROLE_EXISTED, HttpStatus.FOUND.value());
        }
        return Role.builder()
                .id(roleName)
                .description(description)
                .build();
    }

    public ApiResponse<CreateRoleResponseData> createRole(CreateRoleCommand createRoleCommand) {
        Role newRole = verifyRoleNotExists(createRoleCommand.getName(), createRoleCommand.getDescription());
        Role savedRole = roleRepository.save(newRole);
        return ApiResponse.<CreateRoleResponseData>builder()
                .message(String.format("Role with name %s is successfully created", savedRole.getId()))
                .data(CreateRoleResponseData.builder()
                        .name(savedRole.getId())
                        .description(savedRole.getDescription())
                        .build())
                .build();
    }
}
