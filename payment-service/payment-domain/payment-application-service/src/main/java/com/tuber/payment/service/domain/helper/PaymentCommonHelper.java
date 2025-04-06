package com.tuber.payment.service.domain.helper;

import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.entity.Payment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

//TODO: Implement this class
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentCommonHelper {

    public CreditEntry verifyCreditEntryOfCustomerExists(String customerId) {
        return null;
    }

    public List<CreditHistory> verifyCreditHistoryOfCustomerExists(String customerId) {
        return null;
    }

    public Payment verifyPaymentOfOrderExists(UUID orderId) {
        return null;
    }
}
