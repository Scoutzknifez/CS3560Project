package com.CS3560Project.sqlworkers.conditions;

import com.CS3560Project.sqlworkers.SQLHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Conditional {
    private String phrase;

    public Conditional(String field, String value) {
        setPhrase(SQLHelper.makeConditional(field, value));
    }

    @Override
    public String toString() {
        return getPhrase();
    }
}
