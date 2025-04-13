package com.tuber.payment.service.dataaccess.entry.adapter;

import com.tuber.payment.service.dataaccess.entry.mapper.CreditEntryDataAccessMapper;
import com.tuber.payment.service.dataaccess.entry.repository.CreditEntryJpaRepository;
import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.ports.output.repository.CreditEntryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditEntryRepositoryImpl implements CreditEntryRepository {
    CreditEntryJpaRepository creditEntryJpaRepository;
    CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper.creditEntryJpaEntityToCreditEntry(
                creditEntryJpaRepository.save(
                        creditEntryDataAccessMapper.creditEntryToCreditEntryJpaEntity(creditEntry)
                )
        );
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(UUID customerId) {
        return creditEntryJpaRepository.findByCustomerId(customerId)
                .map(creditEntryDataAccessMapper::creditEntryJpaEntityToCreditEntry);
    }
}
