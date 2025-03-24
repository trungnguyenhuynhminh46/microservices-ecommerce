package com.tuber.order.service.dataaccess.voucher.adapter;

import com.tuber.order.service.domain.entity.Voucher;
import com.tuber.order.service.domain.ports.output.repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherRepositoryImpl implements VoucherRepository {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public Set<Voucher> findAllById(Set<UUID> voucherIds) {
        return null;
    }

    @Override
    public Set<Voucher> saveAll(Set<Voucher> vouchers) {
        return null;
    }
}
