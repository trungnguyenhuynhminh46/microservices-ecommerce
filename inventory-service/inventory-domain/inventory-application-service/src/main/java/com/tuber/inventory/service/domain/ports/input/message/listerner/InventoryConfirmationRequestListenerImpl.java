package com.tuber.inventory.service.domain.ports.input.message.listerner;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.helper.InventoryConfirmationMessageHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationRequestListenerImpl implements InventoryConfirmationRequestListener {
    InventoryConfirmationMessageHelper inventoryConfirmationMessageHelper;
    @Override
    public void confirmGoodsAvailable(InventoryConfirmationRequest inventoryConfirmationRequest) {
        inventoryConfirmationMessageHelper.persistFulfillmentHistory(inventoryConfirmationRequest);
    }
}
