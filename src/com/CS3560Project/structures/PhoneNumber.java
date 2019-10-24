package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneNumber {
    private int areaCode;
    private int number;

    @Override
    public String toString() {
        return "+1(" + getAreaCode() + ")" + (getNumber() / 10000) + "-" + (getNumber() % 10000);
    }
}