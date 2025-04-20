package com.tuber.order.service.domain.saga;

import com.tuber.order.service.domain.dto.message.broker.InventoryConfirmationResponse;
import com.tuber.saga.SagaStep;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//TODO: Implement this class
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationSaga implements SagaStep<InventoryConfirmationResponse> {
    @Override
    public void process(InventoryConfirmationResponse data) {

    }

    @Override
    public void rollback(InventoryConfirmationResponse data) {

    }
}
