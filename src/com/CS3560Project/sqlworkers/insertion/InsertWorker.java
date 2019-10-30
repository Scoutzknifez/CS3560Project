package com.CS3560Project.sqlworkers.insertion;

import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.Worker;
import com.CS3560Project.sqlworkers.insertion.types.BaseInsertion;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertWorker extends Worker {
    private BaseInsertion baseInsertion;

    public InsertWorker(Table table, BaseInsertion bi) {
        super(table);
        setBaseInsertion(bi);
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeConnection();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getBaseInsertion().getStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}