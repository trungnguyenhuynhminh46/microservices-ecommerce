package com.tuber.inventory.service.dataaccess.fulfillment.adapter;

import com.tuber.inventory.service.dataaccess.fulfillment.mapper.FulfillmentHistoryJpaDataMapper;
import com.tuber.inventory.service.dataaccess.fulfillment.repository.FulfillmentHistoryJpaRepository;
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
    FulfillmentHistoryJpaRepository fulfillmentHistoryJpaRepository;
    FulfillmentHistoryJpaDataMapper fulfillmentHistoryJpaDataMapper;

    @Override
    public FulfillmentHistory save(FulfillmentHistory fulfillmentHistory) {
        return fulfillmentHistoryJpaDataMapper
                .fulfillmentHistoryJpaEntityToFulfillmentHistory(
                        fulfillmentHistoryJpaRepository.save(
                                fulfillmentHistoryJpaDataMapper.fulfillmentHistoryToFulfillmentHistoryJpaEntity(fulfillmentHistory)
                        )
                );
    }
}
