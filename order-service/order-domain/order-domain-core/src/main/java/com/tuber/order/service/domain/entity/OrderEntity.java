package com.tuber.order.service.domain.entity;

import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.enums.DiscountType;
import com.tuber.domain.valueobject.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderEntity extends AggregateRoot<UniqueUUID> {
    private String trackingId;
    private UUID creatorId;
    private Set<OrderItem> orderItems = new HashSet<>();
    private Set<Voucher> vouchers = new HashSet<>();
    private Money totalPrice;
    private Money finalPrice;
    private OrderStatus orderStatus;
    private List<String> failureMessages = new ArrayList<>();
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private OrderEntity(Builder builder) {
        super.setId(builder.id);
        setTrackingId(builder.trackingId);
        setCreatorId(builder.creatorId);
        setOrderItems(builder.orderItems);
        setVouchers(builder.vouchers);
        setTotalPrice(builder.totalPrice);
        setFinalPrice(builder.finalPrice);
        setOrderStatus(builder.orderStatus);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Money getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Money finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    public UUID getOrderId() {
        return this.getId().getValue();
    }


    public static final class Builder {
        private UniqueUUID id;
        private String trackingId;
        private UUID creatorId;
        private Set<OrderItem> orderItems;
        private Set<Voucher> vouchers;
        private Money totalPrice;
        private Money finalPrice;
        private OrderStatus orderStatus;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder trackingId(String val) {
            trackingId = val;
            return this;
        }

        public Builder creatorId(UUID val) {
            creatorId = val;
            return this;
        }

        public Builder orderItems(Set<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder vouchers(Set<Voucher> val) {
            vouchers = val;
            return this;
        }

        public Builder totalPrice(Money totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder finalPrice(Money val) {
            finalPrice = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
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

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_FOR_PAYMENT, 500, getId().getValue());
        }
        orderStatus = OrderStatus.PAID;
    }

    public void confirmReadyForFulfillment() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_TO_BE_READY_FOR_FULFILLMENT, 500, getId().getValue());
        }
        orderStatus = OrderStatus.READY_FOR_FULFILLMENT;
    }

    public void initCancelling(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_FOR_INIT_CANCELLING, 500, getId().getValue());
        }
        orderStatus = OrderStatus.CANCELLING;
        addFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.CANCELLING && orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_FOR_CANCELING_PAYMENT, 500, getId().getValue());
        }
        orderStatus = OrderStatus.CANCELLED;
        addFailureMessages(failureMessages);
    }

    protected void addFailureMessages(List<String> failureMessages) {
        if (failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
    }

    protected String generateTrackingId(String username) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String timestamp = now.format(formatter);

        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);

        return String.format("%s_%s_%d", timestamp, username, randomNum);
    }

    protected Money calculateTotalPrice() {
        return orderItems.stream().reduce(
                Money.ZERO,
                (subtotal, orderItem) -> subtotal.add(orderItem.getSubTotal()),
                Money::add
        );
    }

    protected Money calculateFinalPrice() {
        Money totalPrice = orderItems.stream().reduce(
                Money.ZERO,
                (subtotal, orderItem) -> subtotal.add(orderItem.getSubTotal()),
                Money::add
        );
        return useVouchers(totalPrice);
    }

    public boolean isValidForInitialization() {
        return getId() == null && getTrackingId() == null
                && getCreatorId() != null && getOrderItems() != null
                && getFinalPrice() == null && getTotalPrice() == null
                && getOrderStatus() == OrderStatus.PENDING
                && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validateOrderItems() {
        if (getOrderItems() != null && getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one order item");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem : getOrderItems()) {
            orderItem.selfValidate().selfInitialize(itemId++, getId().getValue());
        }
    }

    private void initializeVouchers() {
        if (getVouchers() == null) {
            setVouchers(new HashSet<>());
        }
    }

    public OrderEntity selfValidate() {
        if (!isValidForInitialization()) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateOrderItems();
        return this;
    }

    public OrderEntity selfInitialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setTrackingId(generateTrackingId(getCreatorId().toString()));
        initializeOrderItems();
        initializeVouchers();
        setTotalPrice(calculateTotalPrice());
        setFinalPrice(calculateFinalPrice());
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());

        return this;
    }

    private boolean isVoucherInvalid(Voucher voucher, Money finalPrice) {
        boolean achievedMinimumOrderAmount = voucher.getMinimumOrderAmount() == null || finalPrice.isGreaterThanOrEqual(voucher.getMinimumOrderAmount());
        return !voucher.getActive()
                || voucher.getExpiryDate().isBefore(LocalDate.now())
                || voucher.getRemain() <= 0
                || !achievedMinimumOrderAmount;
    }

    public Money useVouchers(Money price) {
        if (vouchers == null || vouchers.isEmpty()) {
            return price;
        }

        Money discountedPrice = price;
        for (Voucher voucher : vouchers) {
            if (isVoucherInvalid(voucher, discountedPrice)) {
                continue;
            }

            discountedPrice = applyDiscount(voucher, discountedPrice);
            updateVoucherUsage(voucher);

            if (discountedPrice.isSmallerThanOrEqualToZero()) {
                return Money.ZERO;
            }
        }

        return discountedPrice;
    }

    private Money applyDiscount(Voucher voucher, Money price) {
        if (voucher.getDiscountType() == DiscountType.FIXED_AMOUNT && voucher.getDiscountAmount() != null) {
            return price.subtract(voucher.getDiscountAmount());
        }

        if (voucher.getDiscountType() == DiscountType.PERCENTAGE && voucher.getDiscountPercentage() != null) {
            Money discountAmount = calculatePercentageDiscount(price, voucher.getDiscountPercentage());
            Money maxDiscount = voucher.getMaximumDiscountAmount();
            return price.subtract(discountAmount.isGreaterThan(maxDiscount) ? maxDiscount : discountAmount);
        }

        return price;
    }

    private Money calculatePercentageDiscount(Money price, BigDecimal discountPercentage) {
        return new Money(price.getAmount().multiply(discountPercentage));
    }

    private void updateVoucherUsage(Voucher voucher) {
        int newRemain = voucher.getRemain() - 1;
        voucher.setRemain(newRemain);
        voucher.setActive(newRemain > 0);
    }
}
