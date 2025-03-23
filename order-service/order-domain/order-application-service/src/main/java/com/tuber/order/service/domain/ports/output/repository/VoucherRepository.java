package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.entity.Voucher;

import java.util.Set;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Set<Voucher> findAllById(Set<UUID> voucherIds);
    Set<Voucher> saveAll(Set<Voucher> vouchers);
}
