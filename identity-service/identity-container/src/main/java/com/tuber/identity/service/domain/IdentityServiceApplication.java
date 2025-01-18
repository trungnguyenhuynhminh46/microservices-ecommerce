package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.PermissionsDefault;
import com.tuber.domain.valueobject.enums.RolesDefault;
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
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@EnableFeignClients(basePackages = "com.tuber.identity.service.domain.ports.output.http.client")
@EnableJpaRepositories(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@EntityScan(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityServiceApplication implements CommandLineRunner {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    UserAccountRepository userAccountRepository;
    CreateUserAccountHelper createUserAccountHelper;

    private final Map<RolesDefault, Set<PermissionsDefault>> roleDefaultSetMap = Map.of(
            RolesDefault.ADMIN, Set.of(
                    PermissionsDefault.GET_USERS_INFO, PermissionsDefault.CREATE_USER, PermissionsDefault.DELETE_USER, PermissionsDefault.UPDATE_USER,
                    PermissionsDefault.GET_ROLES, PermissionsDefault.CREATE_ROLE, PermissionsDefault.UPDATE_ROLE, PermissionsDefault.DELETE_ROLE,
                    PermissionsDefault.GET_PERMISSIONS, PermissionsDefault.CREATE_PERMISSION, PermissionsDefault.UPDATE_PERMISSION, PermissionsDefault.DELETE_PERMISSION
            ),
            RolesDefault.USER, Set.of(PermissionsDefault.GET_AVAILABLE_DRIVERS, PermissionsDefault.BOOK_RIDE, PermissionsDefault.CANCEL_RIDE),
            RolesDefault.DRIVER, Set.of(PermissionsDefault.GET_AVAILABLE_BOOKS, PermissionsDefault.ACCEPT_RIDE, PermissionsDefault.CANCEL_RIDE)
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
        for (PermissionsDefault permissionDefault : PermissionsDefault.values()) {
            Permission permission = Permission.builder()
                    .id(permissionDefault.toString())
                    .description("Permission " + permissionDefault)
                    .build();
            permissionRepository.save(permission);
        }
    }

    private void initializeRoles() {
        for (Map.Entry<RolesDefault, Set<PermissionsDefault>> entry : roleDefaultSetMap.entrySet()) {
            String roleName = entry.getKey().toString();
            Set<PermissionsDefault> permissions = entry.getValue();
            Role role = Role.builder()
                    .id(roleName)
                    .description("Role " + roleName)
                    .build();
            roleRepository.save(role);
            assignPermissionsToRole(roleName, permissions);
        }
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
                .password(adminUsername)
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
    private void assignPermissionsToRole(String roleName, Set<PermissionsDefault> permissionNames) {
        for (PermissionsDefault permissionName : permissionNames) {
            roleRepository.assignPermissionsToRole(roleName, permissionName.toString());
        }
    }
}
