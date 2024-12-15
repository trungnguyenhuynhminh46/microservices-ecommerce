package com.tuber.identity.service.domain.helper.role;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserCommand;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserResponseData;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignRoleToUserHelper {
    UserAccountRepository userAccountRepository;

    @Transactional
    public ApiResponse<AssignRoleToUserResponseData> assignRoleToUser(AssignRoleToUserCommand assignRoleToUserCommand) {
        String username = assignRoleToUserCommand.getUsername();
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add(assignRoleToUserCommand.getRoleName());

        Set<Role> newRolesSet = userAccountRepository.assignRolesToUser(username, rolesSet);
        return ApiResponse.<AssignRoleToUserResponseData>builder()
                .data(AssignRoleToUserResponseData.builder().username(username).roles(newRolesSet).build())
                .build();
    }
}
