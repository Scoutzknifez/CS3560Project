package com.CS3560Project.sqlworkers;

import com.CS3560Project.structures.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Table {
    USERS(User.class);

    private Class constructorClass;
}
