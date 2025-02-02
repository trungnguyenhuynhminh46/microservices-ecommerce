package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.Permissions;
import com.tuber.domain.valueobject.enums.RolesDefault;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.http.client.ProfileServiceDataMapper;
import com.tuber.identity.service.domain.ports.output.http.client.ProfileServiceClient;
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
    CreateUserAccountHelper createUserAccountHelper;
    ProfileServiceClient profileServiceClient;
    ProfileServiceDataMapper profileServiceDataMapper;
    UserAccountRepository userAccountRepository;

    private final Map<RolesDefault, Set<Permissions>> roleDefaultSetMap = Map.of(
            RolesDefault.ADMIN, Set.of(
                    Permissions.GET_USERS_INFO, Permissions.CREATE_USER, Permissions.DELETE_USER, Permissions.UPDATE_USER,
                    Permissions.GET_ROLES, Permissions.CREATE_ROLE, Permissions.UPDATE_ROLE, Permissions.DELETE_ROLE,
                    Permissions.GET_PERMISSIONS, Permissions.REASSIGN_PERMISSION
            )
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
        for (Permissions permissionDefault : Permissions.values()) {
            Permission permission = Permission.builder()
                    .id(permissionDefault.toString())
                    .description("Permission " + permissionDefault)
                    .build();
            permissionRepository.save(permission);
        }
    }

    private void initializeRoles() {
        for (Map.Entry<RolesDefault, Set<Permissions>> entry : roleDefaultSetMap.entrySet()) {
            String roleName = entry.getKey().toString();
            Set<Permissions> permissions = entry.getValue();
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
        CreateUserAccountCommand createUserAccountCommand = CreateUserAccountCommand.builder()
                .username(adminUsername)
                .email("admin@gmail.com")
                .password(adminUsername)
                .firstName("Peter")
                .lastName("Parker")
                .dob(LocalDate.of(2001, 6, 4))
                .city("Hồ Chí Minh")
                .build();
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(createUserAccountCommand);
        profileServiceClient.createUserProfile(profileServiceDataMapper.createUserAccountCommandToCreateUserProfileCommand(createUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId()));
    }

    @Transactional
    private void assignPermissionsToRole(String roleName, Set<Permissions> permissionNames) {
        for (Permissions permissionName : permissionNames) {
            roleRepository.assignPermissionsToRole(roleName, permissionName.toString());
        }
    }
}
