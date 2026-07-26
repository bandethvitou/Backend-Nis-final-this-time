package com.sarana_NIS.newTown_backend.model.enums;

public enum PaymentMethod {
    CASH("Cash"),
    BANK_TRANSFER("Bank Transfer"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    CHEQUE("Cheque"),
    ONLINE_PAYMENT("Online Payment"),
    MOBILE_MONEY("Mobile Money"),
    PAYPAL("PayPal");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}