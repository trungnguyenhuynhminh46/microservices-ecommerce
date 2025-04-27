package com.tuber.inventory.service.messaging.mapper;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.outbox.model.InventoryConfirmationResponsePayload;
import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import com.tuber.kafka.order.avro.model.InventoryConfirmationResponseAvroModel;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryMessageMapper {
    //TODO: Implement this method
    public abstract InventoryConfirmationRequest inventoryConfirmationRequestAvroModelToInventoryConfirmationRequest(InventoryConfirmationRequestAvroModel inventoryConfirmationRequestAvroModel);

    //TODO: Implement this method
    public abstract InventoryConfirmationResponseAvroModel inventoryConfirmationResponsePayloadToInventoryConfirmationResponseAvroModel(InventoryConfirmationResponsePayload inventoryConfirmationResponsePayload, UUID sagaId);
}
