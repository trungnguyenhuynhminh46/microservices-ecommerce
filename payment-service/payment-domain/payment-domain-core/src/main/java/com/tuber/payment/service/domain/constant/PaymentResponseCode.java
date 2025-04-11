package com.tuber.payment.service.domain.constant;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class PaymentResponseCode extends ResponseCodeBase {
    protected final String serviceName = "payment";
    public static final PaymentResponseCode PAYMENT_SAVED_FAILED =
            new PaymentResponseCode(1001, "Failed to save payment with id %s");
    public static final PaymentResponseCode PAYMENT_NOT_FOUND =
            new PaymentResponseCode(1002, "Payment of order with id %s not found");
    public static final PaymentResponseCode CREDIT_ENTRY_NOT_FOUND =
            new PaymentResponseCode(1003, "Credit entry of customer with id %s not found");
    public static final PaymentResponseCode CREDIT_HISTORY_NOT_FOUND =
            new PaymentResponseCode(1004, "Credit history of customer with id %s not found");
    public static final PaymentResponseCode NOT_ENOUGH_CREDIT =
            new PaymentResponseCode(1005, "Customer with id %s doesn't have enough credit to make this payment");
    public static final PaymentResponseCode TOTAL_CREDIT_AMOUNT_NOT_MATCH_HISTORY =
            new PaymentResponseCode(1006, "Credit history total is not equal to current credit for customer with id %s");

    protected PaymentResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
