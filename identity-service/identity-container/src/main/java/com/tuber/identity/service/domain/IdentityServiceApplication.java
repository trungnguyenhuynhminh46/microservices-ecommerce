package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.UserPermission;
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
    private final Map<UserPermission, Permission> permissionsMap = Map.of(
            UserPermission.CREATE, Permission.builder().id(UserPermission.CREATE).description("Permission CREATE").build(),
            UserPermission.DELETE, Permission.builder().id(UserPermission.DELETE).description("Permission DELETE").build(),
            UserPermission.UPDATE, Permission.builder().id(UserPermission.UPDATE).description("Permission UPDATE").build()
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
        assignPermissionsToRole("ADMIN", Set.of(UserPermission.CREATE, UserPermission.DELETE, UserPermission.UPDATE));
        assignPermissionsToRole("USER", Set.of(UserPermission.CREATE, UserPermission.UPDATE));
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
    private void assignPermissionsToRole(String roleName, Set<UserPermission> permissionNames) {
        for (UserPermission permissionName : permissionNames) {
            roleRepository.assignPermissionsToRole(roleName, permissionName);
        }
    }
}
