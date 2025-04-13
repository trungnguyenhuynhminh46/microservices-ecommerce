package com.tuber.payment.service.dataaccess.history.adapter;

import com.tuber.payment.service.dataaccess.history.entity.CreditHistoryJpaEntity;
import com.tuber.payment.service.dataaccess.history.mapper.CreditHistoryDataAccessMapper;
import com.tuber.payment.service.dataaccess.history.repository.CreditHistoryJpaRepository;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {
    CreditHistoryJpaRepository creditHistoryJpaRepository;
    CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryJpaEntityToCreditHistory(
                creditHistoryJpaRepository.save(
                        creditHistoryDataAccessMapper.creditHistoryToCreditHistoryJpaEntity(creditHistory)
                )
        );
    }

    @Override
    public Optional<List<CreditHistory>> findAllByCustomerId(UUID customerId) {
        return creditHistoryJpaRepository.findAllByCustomerId(customerId)
                .map(jpaList -> jpaList.stream()
                        .map(creditHistoryDataAccessMapper::creditHistoryJpaEntityToCreditHistory)
                        .toList());
    }
}
