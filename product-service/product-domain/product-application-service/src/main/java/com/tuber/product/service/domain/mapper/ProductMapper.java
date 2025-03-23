package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.application.mapper.JsonNullableMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.Money;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.dto.shared.ProductAttributeDTO;
import com.tuber.product.service.domain.dto.shared.ProductAttributeOptionDTO;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.domain.entity.ProductAttribute;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    ObjectMapper objectMapper;

    public abstract Product createProductCommandToProduct(CreateProductCommand createProductCommand);

    public abstract ProductResponseData productToProductResponseData(Product product);

    public ProductsListResponseData productsListToProductsListResponseData(List<Product> products) {
        return ProductsListResponseData.builder()
                .products(products.stream()
                        .map(this::productToProductResponseData)
                        .toList())
                .total(products.size())
                .build();
    }

    public Product updateProduct(ModifyProductCommand data, Product product) {
        product.setUpdatedAt(LocalDate.now());
        this.updateProductFields(data, product);
        product.validateProperties();
        product.initializeProductAttributes();
        return product;
    }

    @InheritConfiguration
    @Mapping(target = "id", ignore = true)
    protected abstract void updateProductFields(ModifyProductCommand data, @MappingTarget Product product);

    public ProductsListResponseData setOfProductsToProductsListResponseData(Set<Product> products) {
        return ProductsListResponseData.builder()
                .products(products.stream()
                        .map(this::productToProductResponseData)
                        .toList())
                .total(products.size())
                .build();
    }

    protected List<ProductAttribute> map(JsonNullable<List<ProductAttributeDTO>> attributes) {
        List<ProductAttributeDTO> attributesList = attributes.orElse(null);
        return attributesList == null ? List.of() : attributesList.stream()
                .map(attribute -> ProductAttribute.builder()
                        .name(attribute.getName())
                        .defaultValue(attribute.getDefaultValue())
                        .options(map(attribute.getOptions()))
                        .build())
                .toList();
    }

    protected String map(List<ProductAttributeOptionDTO> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductAttributeOptionDTO> map(String options) {
        try {
            return objectMapper.readValue(options, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductAttributeOptionDTO.class));
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

    protected UniqueUUID map(UUID uuid) {
        return new UniqueUUID(uuid);
    }

    protected Money map(BigDecimal bigDecimal) {
        return new Money(bigDecimal);
    }
}
