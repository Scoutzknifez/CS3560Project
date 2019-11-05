package com.CS3560Project.structures;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public String[] fieldsToArray() {
        List<String> fieldList = new ArrayList<>();

        fieldList.add(getID());
        fieldList.add(getFirstName());
        fieldList.add(getLastName());
        fieldList.add(getPhoneNumber().toString());
        fieldList.add(getAddress());
        fieldList.add(getEmail());
        fieldList.add(getPassword());

        return fieldList.stream().toArray(String[]::new);
    }

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

    /**
     * createInstance method required for ANY structure stored in the database
     * Creates an instance of this object with a result set
     * @param set   The resultset to create an object with
     * @return      The object created
     */
    public static User createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String firstName = set.getString("firstName");
            String lastName = set.getString("lastName");
            String phoneNumber = set.getString("phoneNumber");
            String address = set.getString("address");
            String email = set.getString("email");
            String user_password = set.getString("user_password");

            return new User(id, firstName, lastName, PhoneNumber.stringToPhoneNumber(phoneNumber),
                    address, email, user_password);
        } catch (Exception e) {
            Utils.log("Could not parse returned list.");
            throw new ParseFailureException(set, User.class);
        }
    }
}
