package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.dto.inventory.internal.GetInventoryDetailsQuery;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailsResponseData;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.helper.CommonInventoryHelper;
import com.tuber.inventory.service.domain.mapper.InventoryMapper;
import com.tuber.inventory.service.domain.mapper.ProductMapper;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetInventoryDetailsHelper {
    InventoryRepository inventoryRepository;
    ProductMapper productMapper;
    InventoryMapper inventoryMapper;
    CommonInventoryHelper commonInventoryHelper;


    protected void verifyThereAreInventoriesForProducts(Set<ProductIdWithSkuDTO> productIdWithSkuDTOS) {
        if (!inventoryRepository.existsByProductIdsAndSkus(productIdWithSkuDTOS)) {
            throw new InventoryDomainException(InventoryResponseCode.NO_INVENTORY_FOR_SOME_PRODUCTS, HttpStatus.BAD_REQUEST.value());
        }
    }

    protected void updateProductDetailsIntoInventoryDetails(Set<Inventory> inventories, Set<Product> products) {
        inventories.forEach(inventory -> {
            Product product = products.stream()
                    .filter(p -> p.getId().getValue().equals(inventory.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new InventoryDomainException(InventoryResponseCode.NO_INVENTORY_FOR_SOME_PRODUCTS, HttpStatus.BAD_REQUEST.value()));
            inventory.setProduct(product);
        });
    }

    @Transactional
    public ApiResponse<InternalInventoryDetailsResponseData> getInventoryDetails(GetInventoryDetailsQuery getInventoryDetailsQuery) {
        Set<ProductIdWithSkuDTO> productIdWithSku = getInventoryDetailsQuery.getProductIds();
        verifyThereAreInventoriesForProducts(getInventoryDetailsQuery.getProductIds());
        GetProductsRecord productsRecord = commonInventoryHelper.getProductsDetails(productMapper.productIdWithSkuDtoSetToProductIdSet(productIdWithSku));
        Set<Inventory> inventories = inventoryRepository.findAllByProductIdsAndSkusSet(productIdWithSku);
        updateProductDetailsIntoInventoryDetails(inventories, productsRecord.products());
        return ApiResponse.<InternalInventoryDetailsResponseData>builder()
                .message("Successfully fetched inventory details")
                .data(inventoryMapper.inventoriesToInternalInventoryDetailsResponseData(inventories, productsRecord.hasUnavailableProducts()))
                .build();
    }
}
