package com.tuber.payment.service.dataaccess.history.adapter;

import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {
    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return null;
    }

    @Override
    public Optional<List<CreditHistory>> findAllByCustomerId(UUID customerId) {
        return Optional.empty();
    }
}
