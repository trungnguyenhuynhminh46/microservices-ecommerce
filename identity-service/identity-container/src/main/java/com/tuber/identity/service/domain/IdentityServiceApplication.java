package com.tuber.identity.service.domain;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;
import java.util.Set;

@EnableJpaRepositories(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@EntityScan(basePackages = {"com.tuber.identity.service.dataaccess", "com.tuber.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.tuber")
@AllArgsConstructor
public class IdentityServiceApplication implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final Map<UserPermission, Permission> permissionsMap = Map.of(
            UserPermission.CREATE, Permission.builder().id(UserPermission.CREATE).description("Permission CREATE").build(),
            UserPermission.DELETE, Permission.builder().id(UserPermission.DELETE).description("Permission DELETE").build(),
            UserPermission.UPDATE, Permission.builder().id(UserPermission.UPDATE).description("Permission UPDATE").build()
    );

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializePermissions();
        initializeRoles();
    }

    private void initializePermissions() {
        permissionsMap.values().forEach(permissionRepository::save);
    }

    private void initializeRoles() {
        Role userRole = Role.builder().id(UserRole.USER).description("Role USER").build();
        userRole.addPermission(permissionsMap.get(UserPermission.CREATE));
        userRole.addPermission(permissionsMap.get(UserPermission.UPDATE));
        roleRepository.save(userRole);

        Set<Permission> allPermissions = Set.copyOf(permissionsMap.values());
        roleRepository.save(Role.builder()
                .id(UserRole.ADMIN)
                .description("Role ADMIN")
                .permissions(allPermissions)
                .build()
        );
    }
}
