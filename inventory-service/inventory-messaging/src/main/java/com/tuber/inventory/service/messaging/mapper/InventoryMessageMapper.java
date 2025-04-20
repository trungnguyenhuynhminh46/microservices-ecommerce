package com.tuber.inventory.service.messaging.mapper;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class InventoryMessageMapper {
    public abstract InventoryConfirmationRequest inventoryConfirmationRequestAvroModelToInventoryConfirmationRequest(InventoryConfirmationRequestAvroModel inventoryConfirmationRequestAvroModel);
}
