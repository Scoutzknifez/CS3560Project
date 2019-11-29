package com.CS3560Project.sqlworkers.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AndConditional {
    private Conditional condition1;
    private Conditional condition2;

    @Override
    public String toString() {
        return "(" + getCondition1().toString() + " AND " + getCondition2().toString() + ")";
    }
}