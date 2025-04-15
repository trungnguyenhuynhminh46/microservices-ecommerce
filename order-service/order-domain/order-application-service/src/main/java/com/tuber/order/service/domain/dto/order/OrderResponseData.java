package com.tuber.order.service.domain.dto.order;

import com.tuber.order.service.domain.dto.shared.OrderItemDetailDTO;
import com.tuber.order.service.domain.dto.shared.VoucherDetailDTO;
import com.tuber.domain.valueobject.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderResponseData {
    UUID id;
    String trackingId;
    UUID creatorId;
    Set<OrderItemDetailDTO> orderItems;
    Set<VoucherDetailDTO> voucher;
    BigDecimal finalPrice;
    OrderStatus orderStatus;
    String createdAt;
    String updatedAt;
}
