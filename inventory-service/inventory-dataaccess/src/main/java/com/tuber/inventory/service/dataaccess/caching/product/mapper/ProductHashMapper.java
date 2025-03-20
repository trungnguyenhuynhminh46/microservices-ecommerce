package com.tuber.inventory.service.dataaccess.caching.product.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.LongId;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.caching.product.entity.ProductHash;
import com.tuber.inventory.service.domain.entity.Product;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProductHashMapper {
    public abstract ProductHash productEntityToProductHash(Product product);

    public abstract Product productHashToProductEntity(ProductHash productHash);

    public Map<UUID, Product> productHashesToProductsMap(Set<ProductHash> productHashSet) {
        return productHashSet.stream().collect(
                Collectors.toMap(
                        ProductHash::getId,
                        this::productHashToProductEntity
                )
        );
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected BigDecimal map(Money price) {
        return price.getAmount();
    }

    protected Money map(BigDecimal price) {
        return new Money(price);
    }

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected LongId map(Long id) {
        return new LongId(id);
    }

    protected Long map(LongId id) {
        return id.getValue();
    }
}
