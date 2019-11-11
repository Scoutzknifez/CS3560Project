package com.CS3560Project.sqlworkers.insertion.types;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseInsertion {
    private String stringForm;

    public BaseInsertion(Object[] toCompound) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < toCompound.length; i++) {
            Object obj = toCompound[i];
            if (obj instanceof String)
                sb.append("\"" + obj + "\"");
            else
                sb.append(obj);

            if (i != toCompound.length - 1)
                sb.append(",");
        }

        sb.append(")");
        setStringForm(sb.toString());
    }
}