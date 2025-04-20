package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationMessageHelper {
    //TODO: Implement this class
    public void confirmGoodsAvailable(InventoryConfirmationRequest inventoryConfirmationRequest) {

    }
}
