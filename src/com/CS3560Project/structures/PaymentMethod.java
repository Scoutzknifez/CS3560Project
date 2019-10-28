package com.CS3560Project.structures;

public enum PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL;

    public boolean isCard() {
        return this == CREDIT_CARD || this == DEBIT_CARD;
    }

    public boolean isElectronic() {
        return this == PAYPAL;
    }
}