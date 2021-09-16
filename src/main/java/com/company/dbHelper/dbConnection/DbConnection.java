package com.company.dbHelper.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection user() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql:umapalata.eu/fleetfinder", " java", "1q2w3e4r");

        } catch (
                SQLException e) {
            System.out.println("Unable to connect to database");
            e.printStackTrace();
        }
        return connection;

    }
}
