package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserAccountRepository {
   UserAccount save(UserAccount userAccount);
   Boolean existsByUsername(String username);
   Optional<UserAccount> findByUsername(String username);
   Optional<UserAccount> findByEmail(String email);
   Optional<UserAccount> findById(UUID id);
   List<UserAccount> findAll();
   Set<Role> assignRolesToUser(String username, Set<String> roleNames);
   Set<Role> removeRolesFromUser(String username, Set<String> roleNames);
}
