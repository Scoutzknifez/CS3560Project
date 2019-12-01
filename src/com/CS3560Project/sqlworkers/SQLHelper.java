package com.CS3560Project.sqlworkers;

import com.CS3560Project.sqlworkers.insertion.Databasable;

public class SQLHelper {
    /**
     * Returns an object in raw field -> value form
     *
     * Example.
     * A user object with fields id, name, and password would become:
     * (valueOf(id),valueOf(name),valueOf(password))
     *
     * Where valueOf is the data stored in the field itself
     *
     * @param databasableObject Any object that can be databased
     * @return                  A raw string form of the objects values
     */
    public static String databasableToInsertionForm(Databasable databasableObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        Object[] fieldsInArray = databasableObject.fieldsToArray();

        for (int i = 0; i < fieldsInArray.length; i++) {
            Object obj = fieldsInArray[i];
            if (obj instanceof String) {
                String built = "\"" + obj + "\"";
                sb.append(built);
            }
            else
                sb.append(obj);

            if (i != fieldsInArray.length - 1)
                sb.append(",");
        }

        sb.append(")");
        return sb.toString();
    }

    public static String makeConditionalPhrase(String field, Object value) {
        return "`" + field + "` = " + (value instanceof String ?
                "`" + value + "`" :
                value);
    }
}