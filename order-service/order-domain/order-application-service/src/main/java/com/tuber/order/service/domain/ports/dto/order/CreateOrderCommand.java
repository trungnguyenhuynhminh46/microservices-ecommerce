package com.tuber.order.service.domain.ports.dto.order;

import com.tuber.order.service.domain.ports.dto.shared.OrderItemDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateOrderCommand {
    List<OrderItemDTO> orderItems;
    Set<UUID> voucherIds;
}
