package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.UserPermissionDefault;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@EnableJpaRepositories(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@EntityScan(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class IdentityServiceApplication implements CommandLineRunner {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    UserAccountRepository userAccountRepository;
    CreateUserAccountHelper createUserAccountHelper;
    private final Map<UserPermissionDefault, Permission> permissionsMap = Map.of(
            UserPermissionDefault.CREATE_USER, Permission.builder().id(UserPermissionDefault.CREATE_USER.toString()).description("Permission CREATE_USER").build(),
            UserPermissionDefault.DELETE_USER, Permission.builder().id(UserPermissionDefault.DELETE_USER.toString()).description("Permission DELETE_USER").build(),
            UserPermissionDefault.UPDATE_USER, Permission.builder().id(UserPermissionDefault.UPDATE_USER.toString()).description("Permission UPDATE_USER").build()
    );

    private final Map<String, Role> rolesMap = Map.of(
            "ADMIN", Role.builder().id("ADMIN").description("Role ADMIN").build(),
            "USER", Role.builder().id("USER").description("Role USER").build()
    );

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializePermissions();
        initializeRoles();
        initializeAdminUser();
    }

    private void initializePermissions() {
        permissionsMap.values().forEach(permissionRepository::save);
    }

    private void initializeRoles() {
        rolesMap.values().forEach(roleRepository::save);
        assignPermissionsToRole("ADMIN", Set.of(UserPermissionDefault.CREATE_USER, UserPermissionDefault.DELETE_USER, UserPermissionDefault.UPDATE_USER));
        assignPermissionsToRole("USER", Set.of(UserPermissionDefault.CREATE_USER, UserPermissionDefault.UPDATE_USER));
    }

    private void initializeAdminUser() {
        String adminUsername = "admin!m8UFV4pdR";
        if (userAccountRepository.existsByUsername(adminUsername)) {
            return;
        }

        Set<String> rolesSet = new HashSet<>();
        rolesSet.add("ADMIN");

        UserAccount adminUser = UserAccount.builder()
                .username(adminUsername)
                .email("admin@gmail.com")
                .password("admin!m8UFV4pdR")
                .passwordEncoded(false)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
        adminUser.validateUserAccount();
        adminUser.initializeUserAccount();
        createUserAccountHelper.encodePassword(adminUser);
        userAccountRepository.save(adminUser);
        userAccountRepository.assignRolesToUser(adminUsername, rolesSet);
    }

    @Transactional
    private void assignPermissionsToRole(String roleName, Set<UserPermissionDefault> permissionNames) {
        for (UserPermissionDefault permissionName : permissionNames) {
            roleRepository.assignPermissionsToRole(roleName, permissionName.toString());
        }
    }
}
