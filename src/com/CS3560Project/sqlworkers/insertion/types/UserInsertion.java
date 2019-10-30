package com.CS3560Project.sqlworkers.insertion.types;

import com.CS3560Project.structures.User;

public class UserInsertion extends BaseInsertion {
    public UserInsertion(User user) {
        super(user.fieldsToArray());
    }
}