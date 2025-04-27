package com.tuber.inventory.service.messaging.mapper;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.outbox.model.InventoryConfirmationResponsePayload;
import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import com.tuber.kafka.order.avro.model.InventoryConfirmationResponseAvroModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryMessageMapper {
    public abstract InventoryConfirmationRequest inventoryConfirmationRequestAvroModelToInventoryConfirmationRequest(InventoryConfirmationRequestAvroModel inventoryConfirmationRequestAvroModel);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    public abstract InventoryConfirmationResponseAvroModel inventoryConfirmationResponsePayloadToInventoryConfirmationResponseAvroModel(InventoryConfirmationResponsePayload inventoryConfirmationResponsePayload, UUID sagaId);
}
