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
//        PreparedStatement ps;
//        ResultSet rs;
//
        DbHelper.updateDB();
//        int charId = 2;
//        try {
//            ps = DbConnection.user().prepareStatement("SELECT * FROM test WHERE char_id = " + charId);
//            rs = ps.executeQuery();
//
//            System.out.println("\n=====================================");
//            System.out.printf("%-3.5s %-9.12s %-13.13s %-20.24s %-20.24s%n", "id", "name", "picture", "corp", "alliance");
//            System.out.println("-------------------------------------");
//            while (rs.next()) {
//
//                System.out.printf("%-3.5s %-9.12s %-13.13s %-20.24s %-20.24s%n", rs.getInt("char_id"), rs.getString("char_name"),
//                        rs.getString("char_image"), rs.getString("corp_name"), rs.getString("ally_name"));
//                System.out.println("=====================================");
//            }
//        } catch (SQLException throwables) {
////            throwables.printStackTrace();
//            OutputMessages.error();
//        }
    }
}
