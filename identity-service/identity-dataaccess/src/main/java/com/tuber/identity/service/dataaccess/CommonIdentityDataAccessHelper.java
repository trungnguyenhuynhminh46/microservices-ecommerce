package com.tuber.identity.service.dataaccess;

import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.permission.repository.PermissionJpaRepository;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.dataaccess.role.repository.RoleJpaRepository;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import com.tuber.identity.service.dataaccess.user.repository.UserAccountJpaRepository;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.exception.RoleNotFoundException;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
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
public class CommonIdentityDataAccessHelper {
    UserAccountJpaRepository userAccountJpaRepository;
    RoleJpaRepository roleJpaRepository;
    PermissionJpaRepository permissionJpaRepository;

    public UserAccountJpaEntity verifyUserAccountWithUsernameExists(String username) {
        Optional<UserAccountJpaEntity> userAccountJpa = userAccountJpaRepository.findByUsername(username);
        if (userAccountJpa.isEmpty()) {
            log.warn("Could not find user account with username: {}", username);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

        return userAccountJpa.get();
    }

    public RoleJpaEntity verifyRoleExists(String roleName) {
        Optional<RoleJpaEntity> roleJpa = roleJpaRepository.findByName(roleName);
        if (roleJpa.isEmpty()){
            log.warn("Could not find role with name: {}", roleName);
            throw new RoleNotFoundException(IdentityResponseCode.ROLE_NOT_EXISTS, HttpStatus.NOT_FOUND.value());
        }

        return roleJpa.get();
    }

    public PermissionJpaEntity verifyPermissionExists(String permissionName) {
        Optional<PermissionJpaEntity> permissionJpa = permissionJpaRepository.findByName(permissionName);
        if (permissionJpa.isEmpty()){
            log.warn("Could not find permission with name: {}", permissionName);
            throw new RoleNotFoundException(IdentityResponseCode.PERMISSION_NOT_EXISTS, HttpStatus.NOT_FOUND.value());
        }

        return permissionJpa.get();
    }
}
