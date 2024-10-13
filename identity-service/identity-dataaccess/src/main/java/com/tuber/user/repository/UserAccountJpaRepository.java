package com.tuber.user.repository;

import com.tuber.user.entity.UserAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountJpaRepository extends JpaRepository<UserAccountJpaEntity, UUID> {
}
