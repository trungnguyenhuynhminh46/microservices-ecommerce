package com.tuber.order.service.domain.helper;

import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.dto.http.client.inventory.GetInventoryDetailsQuery;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailResponseData;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailsResponseData;
import com.tuber.order.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.ports.output.http.client.InventoryServiceClient;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonOrderHelper {
    InventoryServiceClient inventoryServiceClient;

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
        //TODO: Implement this method
        return null;
    }

    public void checkIfVouchersAreValid(Set<UUID> voucherIds) {
        //TODO: Implement this method
    }
}
