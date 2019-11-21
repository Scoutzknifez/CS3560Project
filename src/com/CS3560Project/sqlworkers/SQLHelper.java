package com.CS3560Project.sqlworkers;

import com.CS3560Project.sqlworkers.insertion.Databasable;

public class SQLHelper {
    public static String databasableToInsertionForm(Databasable databasableObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < databasableObject.fieldsToArray().length; i++) {
            Object obj = databasableObject.fieldsToArray()[i];
            if (obj instanceof String)
                sb.append("\"" + obj + "\"");
            else
                sb.append(obj);

            if (i != databasableObject.fieldsToArray().length - 1)
                sb.append(",");
        }

        sb.append(")");
        return sb.toString();
    }
}