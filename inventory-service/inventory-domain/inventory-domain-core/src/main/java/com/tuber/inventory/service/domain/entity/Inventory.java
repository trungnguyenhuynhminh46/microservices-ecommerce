package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.entity.ProductAttribute;
import com.tuber.domain.util.ProductUtility;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.constant.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.valueobject.ProductAssignedAttribute;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Inventory extends AggregateRoot<UniqueUUID> {
    private ProductUtility productUtility = new ProductUtility();
    private Product product;
    private String sku;
    private List<ProductAssignedAttribute> assignedAttributes;
    private UUID warehouseId;
    private Integer stockQuantity;
    private String creator;
    private String updater;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Inventory(Builder builder) {
        super.setId(builder.id);
        product = builder.product;
        sku = builder.sku;
        warehouseId = builder.warehouseId;
        stockQuantity = builder.stockQuantity;
        creator = builder.creator;
        updater = builder.updater;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private UniqueUUID id;
        private Product product;
        private String sku;
        private UUID warehouseId;
        private Integer stockQuantity;
        private String creator;
        private String updater;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder warehouseId(UUID val) {
            warehouseId = val;
            return this;
        }

        public Builder stockQuantity(Integer val) {
            stockQuantity = val;
            return this;
        }

        public Builder creator(String val) {
            creator = val;
            return this;
        }

        public Builder updater(String val) {
            updater = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDate val) {
            updatedAt = val;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAssignedAttributes(List<ProductAssignedAttribute> assignedAttributes) {
        List<ProductAttribute> attributes = getProduct().getAttributes();
        for (ProductAssignedAttribute assignedAttribute : assignedAttributes) {
            if (attributes.stream().noneMatch(attribute -> attribute.getName().equals(assignedAttribute.getName()))) {
                throw new IllegalArgumentException(String.format("Product %s does not have attribute %s", getProduct().getId(), assignedAttribute.getName()));
            }
        }

        for (ProductAttribute attribute: attributes) {
            if (assignedAttributes.stream().noneMatch(assignedAttribute -> assignedAttribute.getName().equals(attribute.getName()))) {
                assignedAttributes.add(new ProductAssignedAttribute(attribute.getName(), attribute.getDefaultValue()));
            }
        }

        assignedAttributes.sort(Comparator.comparing(ProductAssignedAttribute::getName));
        this.assignedAttributes = assignedAttributes;
        this.sku = productUtility.encodeAttributesToSku(assignedAttributes.stream().collect(Collectors.toMap(ProductAssignedAttribute::getName, ProductAssignedAttribute::getValue)));
    }

    public List<ProductAssignedAttribute> getAssignedAttributes() {
        return assignedAttributes;
    }

    public void setSku(String sku) {
        Map<String, String> mapAssignedAttributes = productUtility.decodeSkuToAttributes(sku);
        List<ProductAttribute> attributes = getProduct().getAttributes();

        mapAssignedAttributes.forEach((name, value) -> {
            if (attributes.stream().noneMatch(attribute -> attribute.getName().equals(name))) {
                throw new IllegalArgumentException(String.format("Product %s does not have attribute %s", getProduct().getId(), name));
            }
        });

        this.assignedAttributes = mapAssignedAttributes.entrySet().stream()
                .map(entry -> new ProductAssignedAttribute(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }

    public boolean isValidForInitialization() {
        return getProduct() != null && getSku() != null
                && getWarehouseId() != null && getStockQuantity() != null
                && getCreator() != null && getUpdater() != null && getId() == null
                && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validateStockQuantity() {
        if( getStockQuantity() == null || getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be less than 0");
        }
    }

    public void validateProperties() {
        validateStockQuantity();
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateProperties();
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }

    public void addStock(Integer quantity, String updater) {
        setStockQuantity(getStockQuantity() + quantity);
        setUpdater(updater);
        setUpdatedAt(LocalDate.now());
    }

    public void removeStock(Integer quantity, String updater) {
        setStockQuantity(getStockQuantity() - quantity);
        setUpdater(updater);
        setUpdatedAt(LocalDate.now());
    }
}
