package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.role.DeleteRoleCommand;
import com.tuber.identity.service.domain.dto.role.DeleteRoleResponseData;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteRoleHelper {
    RoleRepository roleRepository;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    @Transactional
    public ApiResponse<DeleteRoleResponseData> deleteRole(DeleteRoleCommand deleteRoleCommand) {
        Role savedRole = commonIdentityServiceHelper.verifyRoleExist(deleteRoleCommand.getName());
        roleRepository.delete(savedRole);
        return ApiResponse.<DeleteRoleResponseData>builder()
                .message(String.format("Role with name %s is successfully deleted", deleteRoleCommand.getName()))
                .data(DeleteRoleResponseData.builder().deletedRole(savedRole).build())
                .build();
    }
}
