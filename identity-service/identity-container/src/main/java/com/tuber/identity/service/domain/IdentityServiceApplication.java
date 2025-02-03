package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.Permissions;
import com.tuber.domain.valueobject.enums.RolesDefault;
import com.tuber.identity.service.domain.dto.user.account.AssignRoleToUserCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.role.AssignRoleToUserHelper;
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
import java.util.List;
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
    AssignRoleToUserHelper assignRoleToUserHelper;
    ProfileServiceClient profileServiceClient;
    ProfileServiceDataMapper profileServiceDataMapper;
    UserAccountRepository userAccountRepository;

    private final Map<RolesDefault, Set<Permissions>> roleDefaultSetMap = Map.of(
            RolesDefault.ADMIN, Set.of(
                    Permissions.GET_USERS_INFO, Permissions.CREATE_USER, Permissions.DELETE_USER, Permissions.UPDATE_USER,
                    Permissions.GET_ROLES, Permissions.CREATE_ROLE, Permissions.UPDATE_ROLE, Permissions.DELETE_ROLE,
                    Permissions.GET_PERMISSIONS, Permissions.REASSIGN_PERMISSION
            ),
            RolesDefault.STORE_ADMIN, Set.of(
                    Permissions.GET_PRODUCT_CATEGORY, Permissions.CREATE_PRODUCT_CATEGORY, Permissions.UPDATE_PRODUCT_CATEGORY, Permissions.DELETE_PRODUCT_CATEGORY
            )
    );

    private final List<UserData> usersData = List.of(
            UserData.builder()
                    .username("admin!m8UFV4pdR")
                    .email("admin@gmail.com")
                    .password("admin!m8UFV4pdR")
                    .firstName("Admin")
                    .lastName("Super")
                    .dob(LocalDate.of(2001, 6, 4))
                    .city("Hồ Chí Minh")
                    .roleName(List.of(RolesDefault.ADMIN.toString(), RolesDefault.STORE_ADMIN.toString()))
                    .build(),
            UserData.builder()
                    .username("store_admin!m8UFV4pdR")
                    .email("store_admin@gmail.com")
                    .password("store_admin!m8UFV4pdR")
                    .firstName("Admin")
                    .lastName("Store")
                    .dob(LocalDate.of(1996, 3, 20))
                    .city("Hà Nội")
                    .roleName(List.of(RolesDefault.STORE_ADMIN.toString()))
                    .build()
    );

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializePermissions();
        initializeRoles();
        initializeUsers();
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

    private void initializeUsers() {
        for (UserData userData : usersData) {
            initializeUser(userData);
        }
    }

    @Transactional
    private void initializeUser(UserData userData) {
        if (userAccountRepository.existsByUsername(userData.getUsername())) {
            return;
        }
        CreateUserAccountCommand createUserAccountCommand = CreateUserAccountCommand.builder()
                .username(userData.getUsername())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .dob(userData.getDob())
                .city(userData.getCity())
                .build();
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(createUserAccountCommand);
        profileServiceClient.createUserProfile(profileServiceDataMapper.createUserAccountCommandToCreateUserProfileCommand(createUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId()));

        assignRolesToUser(userData.getUsername(), userData.getRoleName());
    }

    private void assignRolesToUser(String username, List<String> roleNames) {
        for (String roleName : roleNames) {
            AssignRoleToUserCommand assignRoleToUserCommand = AssignRoleToUserCommand.builder()
                    .username(username)
                    .roleName(roleName)
                    .build();
            assignRoleToUserHelper.assignRoleToUser(assignRoleToUserCommand);
        }
    }

    @Transactional
    private void assignPermissionsToRole(String roleName, Set<Permissions> permissionNames) {
        for (Permissions permissionName : permissionNames) {
            roleRepository.assignPermissionsToRole(roleName, permissionName.toString());
        }
    }
}
