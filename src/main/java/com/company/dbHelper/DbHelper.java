package com.company.dbHelper;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHelper {
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void updateDB() {
        int charID = 2;
        int corpID = 20;
        String charName = "John Doe";
        String corpName = "NewbieCorp";
        String allyName = "NewbieAlly";
        int allyID = 123;
        String charImage = "image.url";

        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM test WHERE char_id = " + charID);
            rs = ps.executeQuery();
            System.out.println("retrived id " + charID);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("char_id");
                System.out.println("Id from database:" + id);
                String idString = Integer.toString(rs.getInt("char_id"));
                System.out.println("id to string: " + idString);
                System.out.println("id from database " + id);
                if (id == charID) {
                    try {
                        ps = DbConnection.user().prepareStatement("UPDATE test SET corp_name = '" + corpName + "' WHERE char_id = " + charID);
                        ps.execute();
                    } catch (Exception e) {
                        //                           e.printStackTrace();
                        OutputMessages.error();
                        System.out.println("true");
                    }
                }
            }
            if (id != charID) {
                System.out.println("hardcoded check id " + id);
                try {
                    ps = DbConnection.user().prepareStatement("INSERT INTO test (char_id, char_name, char_image, corp_id, corp_name, ally_id, ally_name)" +
                            " VALUES (" + charID + ", '" + charName + "', '" + charImage + "', " + corpID +
                            ", '" + corpName + "', " + allyID + ", '" + allyName + "');");
                    ps.execute();
                } catch (Exception e) {
                    //e.printStackTrace();
                    OutputMessages.error();
                    System.out.println("false");
                }
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            OutputMessages.error();
            System.out.println("global");

        }
    }

    public static void activeProfilesBeforeMessage() {
        try {
        ps = DbConnection.user().prepareStatement("SELECT char_image, char_name, corp_name, ally_name  FROM test WHERE status = 'active'");
        rs = ps.executeQuery();
        System.out.println("\n=====================================");
        System.out.printf("%-50.54s %-50.54s %-20.24s%n", "name",  "corp", "alliance");
        System.out.println("-------------------------------------");
        while (rs.next()) {

            System.out.printf("%-50.54s %-50.54s %-20.24s%n",  rs.getString("char_name"),
                    rs.getString("corp_name"), rs.getString("ally_name"));
            System.out.println("Portrait: " +rs.getString("char_image"));
            System.out.println("=====================================");
        }  } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
