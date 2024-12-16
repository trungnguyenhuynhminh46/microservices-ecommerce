package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserCommand;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserResponseData;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignRoleToUserHelper {
    UserAccountRepository userAccountRepository;
    RoleRepository roleRepository;

    @Transactional
    public ApiResponse<AssignRoleToUserResponseData> assignRoleToUser(AssignRoleToUserCommand assignRoleToUserCommand) {
        String username = assignRoleToUserCommand.getUsername();
        String roleName = assignRoleToUserCommand.getRoleName();
        if (roleRepository.existsByNameAndUsername(username, roleName)) {
            throw new IdentityDomainException(IdentityResponseCode.ROLE_EXISTED_IN_USER_ACCOUNT, HttpStatus.BAD_REQUEST.value(), username, roleName);
        }
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add(roleName);

        Set<Role> newRolesSet = userAccountRepository.assignRolesToUser(username, rolesSet);
        return ApiResponse.<AssignRoleToUserResponseData>builder()
                .data(AssignRoleToUserResponseData.builder().username(username).roles(newRolesSet).build())
                .build();
    }
}
