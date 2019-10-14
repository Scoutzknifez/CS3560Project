package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String ID;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "{ID:\"" + getID() + "\",firstName:\"" + getFirstName() + "\",lastName:\"" + getLastName() + "\"}";
    }
}
