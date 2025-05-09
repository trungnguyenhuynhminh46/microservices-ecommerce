package com.tuber.payment.service.domain.ports.output.repository;

import com.tuber.payment.service.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory);
    Optional<List<CreditHistory>> findAllByCustomerId(UUID customerId);
}
