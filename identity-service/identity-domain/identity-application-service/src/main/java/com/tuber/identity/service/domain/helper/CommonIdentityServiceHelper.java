package com.tuber.identity.service.domain.helper;

import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.AuthenticationException;
import com.tuber.identity.service.domain.exception.PermissionNotFoundException;
import com.tuber.identity.service.domain.exception.RoleNotFoundException;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonIdentityServiceHelper {
    PasswordEncoder passwordEncoder;
    UserAccountRepository userAccountRepository;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    public UserAccount verifyUserAccountWithUsernameExist(String username) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(username);
        if (userAccount.isEmpty()) {
            log.warn("Could not find user account with username: {}", username);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

        return userAccount.get();
    }

    public UserAccount verifyUserAccountWithIdExist(UUID userId) {
        Optional<UserAccount> userAccount = userAccountRepository.findById(userId);
        if (userAccount.isEmpty()) {
            log.warn("Could not find user account with id: {}", userId);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_ID_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return userAccount.get();
    }

    public void verifyPassword(String rawPassword, String encodedPassword) {
        boolean matched = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!matched) {
            throw new AuthenticationException(IdentityResponseCode.FAILED_AUTHENTICATION, HttpStatus.FORBIDDEN.value());
        }
    }

    public Role verifyRoleExist(String roleName) {
        Optional<Role> savedRole = roleRepository.findByName(roleName);
        if (savedRole.isEmpty()) {
            log.warn("Could not find role with name: {}", roleName);
            throw new RoleNotFoundException(IdentityResponseCode.ROLE_NOT_EXISTS, HttpStatus.NOT_FOUND.value());
        }
        return savedRole.get();
    }

    public Permission verifyPermissionExists(String permissionName) {
        Optional<Permission> savedPermission = permissionRepository.findByName(permissionName);
        if (savedPermission.isEmpty()) {
            log.warn("Could not find permission with name: {}", permissionName);
            throw new PermissionNotFoundException(IdentityResponseCode.PERMISSION_NOT_EXISTS, HttpStatus.NOT_FOUND.value());
        }
        return savedPermission.get();
    }
}
