package com.CS3560Project.sqlworkers.insertion.types;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseInsertion {
    private String stringForm;

    public BaseInsertion(String[] toCompound) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < toCompound.length; i++) {
            sb.append("\"" + toCompound[i] + "\"");

            if (i != toCompound.length - 1)
                sb.append(",");
        }

        sb.append(")");
        setStringForm(sb.toString());
    }
}