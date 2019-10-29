package com.CS3560Project.structures;

import com.CS3560Project.exceptions.ParseFailureException;
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

    public static PhoneNumber stringToPhoneNumber(String toPhoneNumber) {
        try {
            String areaCode = "";
            String phoneNumber = "";

            String[] sections = toPhoneNumber.split("\\)");

            areaCode = sections[0].split("\\(")[1].trim();
            phoneNumber = sections[1].replace("-", "").trim();

            return new PhoneNumber(Integer.parseInt(areaCode), Integer.parseInt(phoneNumber));
        } catch (Exception e) {
            throw new ParseFailureException(toPhoneNumber, PhoneNumber.class);
        }
    }
}