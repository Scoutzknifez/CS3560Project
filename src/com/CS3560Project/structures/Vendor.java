package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Vendor {
    private String vendorID;
    private String name;
    private String address;
    private String number;
    private boolean registration;
    private Date RegistrationExpirationDate;
    private int creditScore;
    private String paymentType;
    private String market;
    private int yearsInBusiness;
}
