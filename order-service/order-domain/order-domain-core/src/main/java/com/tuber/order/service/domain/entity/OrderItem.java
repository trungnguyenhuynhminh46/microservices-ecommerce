package com.tuber.order.service.domain.entity;

import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.entity.Warehouse;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.domain.util.ProductUtility;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.ProductAttributeOption;
import com.tuber.order.service.domain.valueobject.OrderItemId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderItem extends BaseEntity<OrderItemId> {
    private ProductUtility productUtility = new ProductUtility();
    UUID orderId;
    Product product;
    String sku;
    Warehouse warehouse;
    Integer quantity;
    Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.id);
        orderId = builder.orderId;
        product = builder.product;
        sku = builder.sku;
        quantity = builder.quantity;
        subTotal = builder.subTotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Money subTotal) {
        this.subTotal = subTotal;
    }

    public static final class Builder {
        private OrderItemId id;
        private UUID orderId;
        private Product product;
        private String sku;
        private Warehouse warehouse;
        private Integer quantity;
        private Money subTotal;

        private Builder() {
        }

        public Builder id(OrderItemId val) {
            id = val;
            return this;
        }

        public Builder id(Long val) {
            id = new OrderItemId(val);
            return this;
        }

        public Builder orderId(UUID val) {
            orderId = val;
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

        public Builder warehouse(Warehouse val) {
            warehouse = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    public Money getPriceAfterAddingOptions() {
        Money price = product.getPrice();
        Map<String, String> assignedOptionsNameMap = productUtility.decodeSkuToAttributes(sku);
        product.getAttributes().forEach(attribute -> {
            List<ProductAttributeOption> options = productUtility.optionsStringToListOfAttributeOptions(attribute.getOptions());
            ProductAttributeOption assignedOption = options.stream()
                    .filter(option -> option.getName().equals(assignedOptionsNameMap.get(attribute.getName())))
                    .findFirst()
                    .orElseThrow(() -> new OrderDomainException(OrderResponseCode.PRODUCT_IN_ORDER_ITEM_IS_OUTDATED, 404));
            price.add(new Money(assignedOption.getChangeAmount()));
        });

        return price.multiply(Double.valueOf(quantity));
    }

    public boolean isValidForInitialization() {
        return getId() == null && getOrderId() == null
                && getProduct() != null && getSku() != null
                && getWarehouse() != null && getQuantity() != null
                && getSubTotal() == null;

    }

    public void validateQuantity() {
        if (getQuantity() != null && getQuantity() < 0) {
            throw new IllegalArgumentException("Order item quantity can not be negative");
        }
    }

    public OrderItem selfValidate() {
        if (!isValidForInitialization()) {
            throw new OrderDomainException(OrderResponseCode.ORDER_ITEM_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateQuantity();
        return this;
    }

    public OrderItem selfInitialize(long itemId, UUID orderId) {
        setId(new OrderItemId(itemId));
        setOrderId(orderId);
        setSubTotal(getPriceAfterAddingOptions().multiply(Double.valueOf(getQuantity())));

        return this;
    }
}
