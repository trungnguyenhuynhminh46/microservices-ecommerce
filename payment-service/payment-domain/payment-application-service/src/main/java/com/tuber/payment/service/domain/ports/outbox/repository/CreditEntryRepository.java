package com.tuber.payment.service.domain.ports.outbox.repository;

import com.tuber.payment.service.domain.entity.CreditEntry;

import java.util.Optional;
import java.util.UUID;

//TODO Implement this class
public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByCustomerId(UUID customerId);
}
