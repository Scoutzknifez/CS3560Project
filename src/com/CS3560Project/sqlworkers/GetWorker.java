package com.CS3560Project.sqlworkers;

import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetWorker extends Worker {
    private List<Object> items;
    private String[] arguments;

    public GetWorker(Table table, String... args) {
        super(table);
        setArguments(args);
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        getFromDatabase();
        if (items == null) {
            Utils.log("Item list is null from GetWorker");
        }

        closeConnection();
    }

    private void getFromDatabase() {
        String sqlArg = "SELECT * FROM " + getTable().name() + (getArguments().length == 0 ? "" : " " + getArguments()[0]); // TODO
        try {
            putResultIntoList(getStatement().executeQuery(sqlArg));
        } catch (Exception e) {
            Utils.log("Failed to fetch list from database.");
        }
    }

    private void putResultIntoList(ResultSet set) {
        items = new ArrayList<>();
        try {
            while (set.next()) {
                items.add(getTable().getConstructorClass().getDeclaredMethod("createInstance", ResultSet.class)
                        .invoke(getTable().getConstructorClass(), set));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Could not parse returned list from " + getTable().name() + " table.");
        }
    }
}