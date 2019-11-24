package com.CS3560Project.structures;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
    private String houseNumber;
    private String city;
    private State state;
    private int zip;

    @Override
    public String toString() {
        return getHouseNumber() + ", " + getCity() + ", " + Utils.capitalize(state.getAbbreviation()) + ", " + getZip();
    }

    public static Address stringToAddress(String toAddress) {
        try {
            String houseNumber, city;
            State state;
            int zip;

            String[] sections = toAddress.split(", ");

            if (sections.length != 4)
                throw new ParseFailureException(toAddress, Address.class);

            houseNumber = sections[0];
            city = sections[1];
            state = State.getStateFromAbbreviation(sections[2]);
            zip = Integer.parseInt(sections[3]);

            return new Address(houseNumber, city, state, zip);
        } catch (Exception e) {
            throw new ParseFailureException(toAddress, Address.class);
        }
    }
}