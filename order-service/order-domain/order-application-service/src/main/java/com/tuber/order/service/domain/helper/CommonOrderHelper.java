package com.tuber.order.service.domain.helper;

import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.dto.http.client.inventory.GetInventoryDetailsQuery;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailResponseData;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailsResponseData;
import com.tuber.order.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.Voucher;
import com.tuber.order.service.domain.ports.output.http.client.InventoryServiceClient;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import com.tuber.order.service.domain.ports.output.repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonOrderHelper {
    InventoryServiceClient inventoryServiceClient;
    OrderRepository orderRepository;
    VoucherRepository voucherRepository;

    protected void verifyAllProductsAreAvailable(InternalInventoryDetailsResponseData internalInventoryDetailsResponseData) {
        boolean allProductsAreInStock = internalInventoryDetailsResponseData.getInventoryDetails().stream().allMatch(inventoryDetail -> inventoryDetail.getStockQuantity() > 0);
        if (allProductsAreInStock && internalInventoryDetailsResponseData.isHasUnavailableProducts()) {
            log.error("Some products are unavailable");
            throw new OrderDomainException(OrderResponseCode.PRODUCT_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Set<InternalInventoryDetailResponseData> getSetOfInventoryDetails(Set<ProductIdWithSkuDTO> productIds) {
        InternalInventoryDetailsResponseData internalInventoryDetailsResponseData = Objects.requireNonNull(inventoryServiceClient.getInventoryDetails(
                GetInventoryDetailsQuery.builder()
                        .productIds(productIds)
                        .build()
        ).getBody()).getData();
        verifyAllProductsAreAvailable(internalInventoryDetailsResponseData);
        return internalInventoryDetailsResponseData.getInventoryDetails();
    }

    public OrderEntity saveOrder(OrderEntity order) {
        OrderEntity savedOrder = orderRepository.save(order);
        if (savedOrder == null) {
            log.error("Failed to save order");
            throw new OrderDomainException(OrderResponseCode.ORDER_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return savedOrder;
    }

    public Set<Voucher> checkIfVouchersAreValid(Set<UUID> voucherIds) {
        Set<Voucher> vouchers = voucherRepository.findAllById(voucherIds);
        if (vouchers.size() != voucherIds.size()) {
            log.error("Some vouchers are invalid");
            throw new OrderDomainException(OrderResponseCode.INVALID_VOUCHER, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        vouchers.forEach(voucher -> {
            if (voucher.getRemain() <= 0 || !voucher.getActive()) {
                log.error("Some vouchers are inactive or out of stock");
                throw new OrderDomainException(OrderResponseCode.INVALID_VOUCHER, HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        });

        return vouchers;
    }

    public Set<Voucher> useVouchers(Set<UUID> voucherIds) {
        Set<Voucher> vouchers = checkIfVouchersAreValid(voucherIds);
        Set<Voucher> updatedVouchers = vouchers.stream().peek(voucher -> {
            voucher.setRemain(voucher.getRemain() - 1);
            voucher.setActive(voucher.getRemain() > 0);
        }).collect(Collectors.toSet());

        return voucherRepository.saveAll(updatedVouchers);
    }
}
