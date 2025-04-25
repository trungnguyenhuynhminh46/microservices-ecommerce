package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;

public interface FulfillmentHistoryRepository {
    FulfillmentHistory save(FulfillmentHistory fulfillmentHistory);
}
