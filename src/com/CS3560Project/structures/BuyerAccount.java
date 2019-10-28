package com.CS3560Project.structures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerAccount extends User {
    private PaymentMethod paymentMethod;

    public BuyerAccount(String id, String firstName, String lastName, PhoneNumber phoneNumber, String address, String email, String password, PaymentMethod paymentMethod) {
        super(id, firstName, lastName, phoneNumber, address, email, password);
        setPaymentMethod(paymentMethod);
    }
}
