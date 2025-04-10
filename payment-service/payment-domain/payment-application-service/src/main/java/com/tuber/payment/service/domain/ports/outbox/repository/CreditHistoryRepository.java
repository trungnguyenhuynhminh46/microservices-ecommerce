package com.tuber.payment.service.domain.ports.outbox.repository;

import com.tuber.payment.service.domain.entity.CreditHistory;

import java.util.Optional;

//TODO Implement this class
public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory);
    Optional<CreditHistory> findByCustomerId(String customerId);
}
