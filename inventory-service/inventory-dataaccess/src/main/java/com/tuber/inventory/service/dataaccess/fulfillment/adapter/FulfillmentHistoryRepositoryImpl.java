package com.tuber.inventory.service.dataaccess.fulfillment.adapter;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.ports.output.repository.FulfillmentHistoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FulfillmentHistoryRepositoryImpl implements FulfillmentHistoryRepository {
    //TODO: Implement this method
    @Override
    public FulfillmentHistory save(FulfillmentHistory fulfillmentHistory) {
        return null;
    }
}
