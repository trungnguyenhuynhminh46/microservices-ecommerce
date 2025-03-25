package com.tuber.order.service.dataaccess.voucher.repository;

import com.tuber.order.service.dataaccess.voucher.entity.VoucherJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherJpaRepository extends JpaRepository<VoucherJpaEntity, UUID> {
}
