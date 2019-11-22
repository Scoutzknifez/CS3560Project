package com.CS3560Project.sqlworkers;

import com.CS3560Project.sqlworkers.insertion.Databasable;

public class SQLHelper {
    public static String databasableToInsertionForm(Databasable databasableObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        Object[] fieldsInArray = databasableObject.fieldsToArray();

        for (int i = 0; i < fieldsInArray.length; i++) {
            Object obj = fieldsInArray[i];
            if (obj instanceof String)
                sb.append("\"" + obj + "\"");
            else
                sb.append(obj);

            if (i != fieldsInArray.length - 1)
                sb.append(",");
        }

        sb.append(")");
        return sb.toString();
    }
}