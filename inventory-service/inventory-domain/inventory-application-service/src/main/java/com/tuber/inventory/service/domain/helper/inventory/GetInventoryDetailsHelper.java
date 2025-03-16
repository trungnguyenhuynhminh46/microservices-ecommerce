package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductsListResponseData;
import com.tuber.inventory.service.domain.dto.inventory.internal.GetInventoryDetailsQuery;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailsResponseData;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.mapper.ProductMapper;
import com.tuber.inventory.service.domain.ports.output.http.client.ProductServiceClient;
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
public class GetInventoryDetailsHelper {
    ProductCachingRepository productCachingRepository;
    ProductMapper productMapper;
    ProductServiceClient productServiceClient;
    InventoryRepository inventoryRepository;

    protected Set<UUID> getNonCachedProductIds(Map<UUID, Product> productsMap) {
        return productsMap.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    protected GetProductsRecord getProductsDetails(Set<UUID> productIds) {
        log.info("Getting products details for product ids: {}", productIds);
        Map<UUID, Product> cachedProducts = productCachingRepository.getProductsByIds(productIds);
        Set<UUID> nonCachedProductIds = getNonCachedProductIds(cachedProducts);
        ProductsListResponseData productsListResponseData = Objects.requireNonNull(productServiceClient.getProductsDetailsByIds(productMapper.productIdsSetToGetProductDetailsQuery(nonCachedProductIds)).getBody()).getData();
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

    protected void verifyThereAreInventoriesForProducts(Set<ProductIdWithSkuDTO> productIdWithSkuDTOS) {
        if (!inventoryRepository.existsByProductIdsAndSkus(productIdWithSkuDTOS)) {
            throw new InventoryDomainException(InventoryResponseCode.NO_INVENTORY_FOR_SOME_PRODUCTS, HttpStatus.BAD_REQUEST.value());
        }
    }

    public ApiResponse<InternalInventoryDetailsResponseData> getInventoryDetails(GetInventoryDetailsQuery getInventoryDetailsQuery) {
        verifyThereAreInventoriesForProducts(getInventoryDetailsQuery.getProductIds());
        GetProductsRecord productsRecord = getProductsDetails(productMapper.productIdWithSkuDtoSetToProductIdSet(getInventoryDetailsQuery.getProductIds()));
        return null;
    }
}
