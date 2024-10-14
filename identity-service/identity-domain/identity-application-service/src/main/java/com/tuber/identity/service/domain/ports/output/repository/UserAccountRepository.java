package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository {
   UserAccount save(UserAccount userAccount);
   Optional<UserAccount> findByUsername(String username);
   Optional<UserAccount> findByEmail(String email);
}
