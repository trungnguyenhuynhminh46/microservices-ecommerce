package com.tuber.order.service.dataaccess.voucher.adapter;

import com.tuber.order.service.dataaccess.voucher.entity.VoucherJpaEntity;
import com.tuber.order.service.dataaccess.voucher.mapper.VoucherDataAccessMapper;
import com.tuber.order.service.dataaccess.voucher.repository.VoucherJpaRepository;
import com.tuber.order.service.domain.entity.Voucher;
import com.tuber.order.service.domain.ports.output.repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherRepositoryImpl implements VoucherRepository {
    VoucherDataAccessMapper voucherDataAccessMapper;
    VoucherJpaRepository voucherJpaRepository;

    @Override
    public Voucher save(Voucher voucher) {
        return voucherDataAccessMapper.voucherJpaEntityToVoucherEntity(
                voucherJpaRepository.save(
                        voucherDataAccessMapper.voucherEntityToVoucherJpaEntity(voucher)
                )
        );
    }

    @Override
    public Set<Voucher> findAllById(Set<UUID> voucherIds) {
        return voucherJpaRepository.findAllById(voucherIds).stream()
                .map(voucherDataAccessMapper::voucherJpaEntityToVoucherEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Voucher> saveAll(Set<Voucher> vouchers) {
        Set<VoucherJpaEntity> voucherJpaEntities = vouchers.stream()
                .map(voucherDataAccessMapper::voucherEntityToVoucherJpaEntity)
                .collect(Collectors.toSet());

        Set<VoucherJpaEntity> savedVoucherJpaEntities = new HashSet<>(voucherJpaRepository.saveAll(voucherJpaEntities));

        return savedVoucherJpaEntities.stream()
                .map(voucherDataAccessMapper::voucherJpaEntityToVoucherEntity)
                .collect(Collectors.toSet());
    }
}
