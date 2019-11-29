package com.CS3560Project.sqlworkers.conditions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AndConditional extends Conditional {
    private Conditional condition2;

    public AndConditional(Conditional condition1, Conditional condition2) {
        super(condition1.toString());
        setCondition2(condition2);
    }

    @Override
    public String toString() {
        return "(" + super.toString() + " AND " + getCondition2().toString() + ")";
    }
}