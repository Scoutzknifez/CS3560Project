package com.CS3560Project.sqlworkers;

import com.CS3560Project.structures.Result;
import com.CS3560Project.utility.Constants;
import com.CS3560Project.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Getter
public abstract class Worker implements Runnable {
    private Statement statement;
    private Connection connection;
    @Setter private Table table;

    public Worker(Table table) {
        setTable(table);
        ready();
    }

    /**
     * Gets the MySQL connector ready to work.
     */
    private void ready() {
        if (connectToDriver() == Result.FAILURE) {
            System.out.println("Could not connect driver for MySQL.");
            return; // TODO should instead throw exceptions rather than returning
        }


        connection = connectToMySQLDatabase();
        if (connection == null) {
            Utils.log("Could not connect to MySQL Database.");
            return;
        }

        statement = getSQLStatement();
        if (statement == null)
            System.out.println("Could not get MySQL statement.");
    }

    /**
     * Gets the driver activated and fails if not present
     * @return  Fail/Success
     */
    private Result connectToDriver() {
        System.out.println("Connecting driver! ");
        try {
            Class.forName(Constants.JDBC_DRIVER);
            return Result.SUCCESS;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Result.FAILURE;
    }

    /**
     * Connects directly to the MySQL database with given credentials.
     * @return  The connection to the database.
     */
    private Connection connectToMySQLDatabase() {
        try {
            return DriverManager.getConnection(Constants.DATABASE_URL, Constants.USERNAME, Constants.PASSWORD);
        } catch (Exception e) {
            Utils.log("Could not connect to MySQL database at " + Constants.DATABASE_URL);
            return null;
        }
    }

    /**
     * Creates a new instance of a statement for the connected database.
     * @return  A statement for the connected database.
     */
    private Statement getSQLStatement() {
        try {
            return getConnection().createStatement();
        } catch (Exception e) {
            System.out.println("Could not create MySQL statement.");
            return null;
        }
    }

    /**
     * Closes the connection to the database safely, ending all open statements.
     */
    protected void closeConnection() {
        try {
            statement.close();
        } catch (Exception e) {
            System.out.println("Could not close statement for connection.");
        }
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Could not close connection.");
        }
    }
}