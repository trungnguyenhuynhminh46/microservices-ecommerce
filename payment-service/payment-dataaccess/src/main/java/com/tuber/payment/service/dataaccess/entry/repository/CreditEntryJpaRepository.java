package com.tuber.payment.service.dataaccess.entry.repository;

import com.tuber.payment.service.dataaccess.entry.entity.CreditEntryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryJpaEntity, UUID> {
    Optional<CreditEntryJpaEntity> findByCustomerId(UUID customerId);
}
