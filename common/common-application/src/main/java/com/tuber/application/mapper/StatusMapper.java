package com.tuber.application.mapper;

import com.tuber.domain.valueobject.enums.OrderStatus;
import com.tuber.domain.valueobject.enums.PaymentStatus;
import com.tuber.saga.SagaStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StatusMapper {
    public SagaStatus[] paymentStatusToSagaStatus(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case PENDING -> null;
            case COMPLETED -> new SagaStatus[] { SagaStatus.STARTED };
            case CANCELLED -> new SagaStatus[] { SagaStatus.PROCESSING };
            case FAILED -> new SagaStatus[] { SagaStatus.STARTED, SagaStatus.PROCESSING };
        };
    }

    public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PAID -> SagaStatus.PROCESSING;
            case APPROVED -> SagaStatus.SUCCEEDED;
            case CANCELLING -> SagaStatus.COMPENSATING;
            case CANCELLED -> SagaStatus.COMPENSATED;
            default -> SagaStatus.STARTED;
        };
    }
}
