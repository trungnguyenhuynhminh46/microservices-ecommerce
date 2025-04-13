package com.tuber.payment.service.dataaccess.entry.adapter;

import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.ports.output.repository.CreditEntryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//TODO Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreditEntryRepositoryImpl implements CreditEntryRepository {
    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return null;
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(UUID customerId) {
        return Optional.empty();
    }
}
