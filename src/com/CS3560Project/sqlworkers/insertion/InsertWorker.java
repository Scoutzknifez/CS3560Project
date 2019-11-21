package com.CS3560Project.sqlworkers.insertion;

import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.Worker;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertWorker extends Worker {
    private String objectStringForm;

    public InsertWorker(Table table, Databasable databasableObject) {
        super(table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeConnection();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}