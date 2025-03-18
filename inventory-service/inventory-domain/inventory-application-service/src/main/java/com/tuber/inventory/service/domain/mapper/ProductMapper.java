package com.tuber.inventory.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.http.client.product.GetProductDetailsQuery;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductAttributeDTO;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductAttributeOption;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductResponseData;
import com.tuber.inventory.service.domain.dto.shared.ProductDTO;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.domain.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    ObjectMapper objectMapper;

    public Product productResponseDataToProductEntity(ProductResponseData productResponseData) {
        return Product.builder()
                .id(productResponseData.getId())
                .name(productResponseData.getName())
                .price(map(productResponseData.getPrice()))
                .attributes(productResponseData.getAttributes().stream()
                        .map(attribute -> productAttributeDtoToProductAttributeEntity(attribute, productResponseData.getId()))
                        .toList())
                .build();
    }

    protected ProductAttribute productAttributeDtoToProductAttributeEntity(ProductAttributeDTO productAttributeDTO, UUID productId) {
        return ProductAttribute.builder()
                .productId(productId)
                .name(productAttributeDTO.getName())
                .defaultValue(productAttributeDTO.getDefaultValue())
                .options(map(productAttributeDTO.getOptions()))
                .build();
    }

    public Set<UUID> productIdWithSkuDtoSetToProductIdSet(Set<ProductIdWithSkuDTO> productIdWithSkuDTOS) {
        return productIdWithSkuDTOS.stream()
                .map(ProductIdWithSkuDTO::getProductId)
                .collect(toSet());
    }

    public GetProductDetailsQuery productIdsSetToGetProductDetailsQuery(Set<UUID> productIds) {
        return GetProductDetailsQuery.builder()
                .productIds(productIds)
                .build();
    }

    public abstract ProductDTO productToProductDTO(Product product);

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected Money map(BigDecimal value) {
        return new Money(value);
    }

    protected String map(List<ProductAttributeOption> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }
}
