package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.valueobject.Money;
import com.tuber.product.service.domain.dto.product.CreateProductCommand;
import com.tuber.product.service.domain.dto.product.ProductAttributeOption;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.dto.product.ProductsListResponseData;
import com.tuber.product.service.domain.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    ObjectMapper objectMapper;

    public abstract Product createProductCommandToProduct(CreateProductCommand createProductCommand);

    public abstract ProductResponseData productToProductResponseData(Product product);

    protected String map(List<ProductAttributeOption> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductAttributeOption> map(String options) {
        try {
            return objectMapper.readValue(options, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductAttributeOption.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }

    protected UUID map(UniqueUUID uniqueUUID) {
        return uniqueUUID.getValue();
    }

    public ProductsListResponseData productsListToProductsListResponseData(List<Product> products) {
        List<ProductResponseData> productResponseDataList = products.stream()
                .map(this::productToProductResponseData)
                .toList();
        Integer total = productResponseDataList.size();
        return ProductsListResponseData.builder()
                .products(productResponseDataList)
                .total(total)
                .build();
    }
}
