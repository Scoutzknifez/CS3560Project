package com.CS3560Project.sqlworkers;

import com.CS3560Project.sqlworkers.conditions.Conditional;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateWorker extends Worker {
    private Conditional conditions;

    public UpdateWorker(Table table, Conditional conditions) {
        super(table);
        setConditions(conditions);
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doUpdate();
        closeConnection();
    }

    private void doUpdate() {
        String sqlArg = "UPDATE " + getTable().name() + " SET `field` = \"value\" WHERE " + getConditions().toString();
        // TODO
    }
}
