package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String ID;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;
    private String address;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "{ID:\"" + getID() +
                "\",firstName:\"" + getFirstName() +
                "\",lastName:\"" + getLastName() +
                "\",phoneNumber:\"" + getPhoneNumber() +
                "\",address:\"" + getAddress() +
                "\",email:\"" + getEmail() +
                "\",password:\"" + getPassword() +
                "\"}";
    }
}
