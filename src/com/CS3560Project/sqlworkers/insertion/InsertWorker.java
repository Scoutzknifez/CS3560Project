package com.CS3560Project.sqlworkers.insertion;

import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.Worker;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InsertWorker extends Worker {
    private boolean listInsertionMode;
    private List<Databasable> listToInsert;
    private String objectStringForm;

    public InsertWorker(Table table, Databasable databasableObject) {
        super(table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    public InsertWorker(Table table, List<Databasable> list) {
        super(table);
        setListInsertionMode(true);
        setListToInsert(list);
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeStatement();
    }

    private void doInsertion() {
        if (listInsertionMode) {
            for(Databasable data : listToInsert) {
                String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + SQLHelper.databasableToInsertionForm(data);
                try {
                    getStatement().execute(sqlArg);
                } catch (Exception e) {
                    Utils.log("Failed to do insertion on table: " + getTable().name());
                }
            }
        } else {
            String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
            try {
                getStatement().execute(sqlArg);
            } catch (Exception e) {
                Utils.log("Failed to do insertion on table: " + getTable().name());
            }
        }
    }
}
