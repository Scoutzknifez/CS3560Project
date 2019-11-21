package com.CS3560Project.sqlworkers.insertion;

public interface Databasable {
    /**
     * Converts the fields to an object array which is pushed to the database
     * @return list of values from fields
     */
    Object[] fieldsToArray();
}