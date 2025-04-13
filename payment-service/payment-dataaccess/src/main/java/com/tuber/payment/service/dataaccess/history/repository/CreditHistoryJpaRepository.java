package com.tuber.payment.service.dataaccess.history.repository;

import com.tuber.payment.service.dataaccess.history.entity.CreditHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditHistoryJpaRepository extends JpaRepository<CreditHistoryJpaEntity, UUID> {
    Optional<List<CreditHistoryJpaEntity>> findAllByCustomerId(UUID customerId);
}
