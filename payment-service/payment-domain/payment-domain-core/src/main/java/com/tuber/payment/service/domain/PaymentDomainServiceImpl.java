package com.tuber.payment.service.domain;

import com.tuber.domain.valueobject.Money;
import com.tuber.payment.service.domain.constant.PaymentResponseCode;
import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.event.PaymentEvent;
import com.tuber.payment.service.domain.exception.PaymentDomainException;
import com.tuber.payment.service.domain.valueobject.enums.TransactionType;
import org.springframework.http.HttpStatus;

import java.util.List;

public class PaymentDomainServiceImpl implements PaymentDomainService {
    // credit entry = credit entry - payment
    // debit = debit + payment
    @Override
    public PaymentEvent validateAndInitializePayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages
    ) {
        payment.selfValidate(failureMessages).selfInitialize();
        creditEntry.subtractPaymentInCreditEntry(payment, failureMessages);
        creditHistories.add(createCreditHistory(payment, TransactionType.DEBIT));
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        return null;
    }

    // credit entry = credit entry + payment
    // credit = credit + payment
    @Override
    public PaymentEvent validateAndCancelPayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages
    ) {
        payment.selfValidate(failureMessages);
        creditEntry.addPaymentInCreditEntry(payment);
        creditHistories.add(createCreditHistory(payment, TransactionType.CREDIT));
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        return null;
    }

    protected CreditHistory createCreditHistory(Payment payment, TransactionType transactionType) {
        return CreditHistory.builder()
                .customerId(payment.getCustomerId())
                .amount(payment.getTotalPrice())
                .transactionType(transactionType)
                .createdAt(payment.getCreatedAt())
                .build();
    }

    protected void validateCreditHistory(
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages
    ) {
        Money totalCredit = getTotalCreditHistoryAmountByType(creditHistories, TransactionType.CREDIT);
        Money totalDebit = getTotalCreditHistoryAmountByType(creditHistories, TransactionType.DEBIT);
        if (totalCredit.isLessThan(totalDebit)) {
            failureMessages.add(String.format("Customer with customer id: %s doesn't have enough credit to make this payment according to credit histories.", creditEntry.getCustomerId()));
            throw new PaymentDomainException(PaymentResponseCode.NOT_ENOUGH_CREDIT, HttpStatus.BAD_REQUEST.value(), creditEntry.getCustomerId());
        }
        if (!totalCredit.subtract(totalDebit).equals(creditEntry.getTotalCreditAmount())) {
            failureMessages.add(String.format("The total credit amount in credit entry doesn't match the total credit in credit historie for customer id: %s.", creditEntry.getCustomerId()));
            throw new PaymentDomainException(PaymentResponseCode.TOTAL_CREDIT_AMOUNT_NOT_MATCH_HISTORY, HttpStatus.BAD_REQUEST.value(), creditEntry.getCustomerId());
        }
    }

    protected Money getTotalCreditHistoryAmountByType(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(history -> history.getTransactionType() == transactionType)
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
