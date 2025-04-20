package com.tuber.order.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationEventPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryConfirmationMessageMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "exportInformationList", source = "inventoryConfirmationEventPayload.productExportInformationDTOList")
    public abstract InventoryConfirmationRequestAvroModel inventoryConfirmationEventToInventoryConfirmationRequestAvroModel(
            InventoryConfirmationEventPayload inventoryConfirmationEventPayload,
            UUID sagaId
    );
}
