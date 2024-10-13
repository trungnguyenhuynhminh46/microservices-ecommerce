package com.tuber.ports.output.repository;

import com.tuber.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository {
   UserAccount save(UserAccount userAccount);
}
