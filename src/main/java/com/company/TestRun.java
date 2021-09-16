package com.company;

import com.company.dbHelper.DbHelper;
import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import com.company.helpers.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestRun {
    public static void main(String[] args) {
        PreparedStatement ps;
        ResultSet rs;

//        DbHelper.updateDB();
        int charId = 96069947;
        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM test WHERE char_id = " + charId);
            rs = ps.executeQuery();

            System.out.println("\n=====================================");
            System.out.printf("%-9.8s %-50.54s %-50.54s %-20.24s%n", "id", "name",  "corp", "alliance");
            System.out.println("-------------------------------------");
            while (rs.next()) {

                System.out.printf("%-9.8s %-50.54s %-50.54s %-20.24s%n", rs.getInt("char_id"), rs.getString("char_name"),
                         rs.getString("corp_name"), rs.getString("ally_name"));
                System.out.println("Portrait: " +rs.getString("char_image"));
                System.out.println("=====================================");
            }
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            OutputMessages.error();
        }
    }
}
