package com.tuber.identity.service.domain.helper.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.RemoveRoleFromUserCommand;
import com.tuber.identity.service.domain.dto.user.account.RemoveRoleFromUserResponseData;
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
public class RemoveRoleFromUserHelper {
    UserAccountRepository userAccountRepository;

    @Transactional
    public ApiResponse<RemoveRoleFromUserResponseData> removeRoleFromUser(RemoveRoleFromUserCommand removeRoleFromUserCommand) {
        String username = removeRoleFromUserCommand.getUsername();
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add(removeRoleFromUserCommand.getRoleName());

        Set<Role> newRolesSet = userAccountRepository.removeRolesFromUser(username, rolesSet);
        return ApiResponse.<RemoveRoleFromUserResponseData>builder()
                .data(RemoveRoleFromUserResponseData.builder().username(username).roles(newRolesSet).build())
                .build();
    }
}
