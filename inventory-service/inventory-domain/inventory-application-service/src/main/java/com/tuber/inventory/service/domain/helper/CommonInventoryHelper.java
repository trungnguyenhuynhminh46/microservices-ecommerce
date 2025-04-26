package com.tuber.inventory.service.domain.helper;

import com.tuber.application.dto.product.ProductsListResponseData;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.helper.inventory.GetProductsRecord;
import com.tuber.inventory.service.domain.mapper.ProductMapper;
import com.tuber.inventory.service.domain.ports.output.http.client.ProductServiceClient;
import com.tuber.inventory.service.domain.ports.output.repository.FulfillmentHistoryRepository;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import com.tuber.inventory.service.domain.ports.output.repository.cache.ProductCachingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonInventoryHelper {
    InventoryRepository inventoryRepository;
    FulfillmentHistoryRepository fulfillmentHistoryRepository;
    ProductCachingRepository productCachingRepository;
    ProductServiceClient productServiceClient;
    ProductMapper productMapper;

    public Inventory saveInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        if (savedInventory == null) {
            log.error(String.format("Failed to save inventory with product id %s", inventory.getProduct().getProductId()));
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), inventory.getProduct().getProductId());
        }
        return savedInventory;
    }

    public FulfillmentHistory saveFulfillmentHistory(FulfillmentHistory fulfillmentHistory) {
        FulfillmentHistory savedFulfillmentHistory = fulfillmentHistoryRepository.save(fulfillmentHistory);
        if (savedFulfillmentHistory == null) {
            log.error(String.format("Failed to save fulfillment history with id %s", fulfillmentHistory.getId()));
        }
        return savedFulfillmentHistory;
    }

    protected Set<UUID> getNonCachedProductIds(Map<UUID, Product> productsMap) {
        return productsMap.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public GetProductsRecord getProductsDetails(Set<UUID> productIds) {
        log.info("Getting products details from cache");
        Map<UUID, Product> cachedProducts = productCachingRepository.getProductsMapById(productIds);
        log.info("Getting non-cached products details through feign client");
        Set<UUID> nonCachedProductIds = getNonCachedProductIds(cachedProducts);
        ProductsListResponseData productsListResponseData =
                Objects.requireNonNull(
                        productServiceClient.getProductsDetailsByIds(
                                productMapper.productIdsSetToGetProductDetailsQuery(nonCachedProductIds)
                        ).getBody()).getData();
        Set<Product> nonCachedProducts = productsListResponseData.getProducts().stream()
                .map(productMapper::productResponseDataToProductEntity)
                .collect(Collectors.toSet());
        productCachingRepository.saveAll(nonCachedProducts);
        boolean hasUnavailableProducts = productsListResponseData.getTotal() < nonCachedProductIds.size();
        Set<Product> allProducts = cachedProducts.values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        allProducts.addAll(nonCachedProducts);
        log.info("Successfully fetched products details for product ids: {}", productIds);
        return new GetProductsRecord(allProducts, hasUnavailableProducts);
    }
}
