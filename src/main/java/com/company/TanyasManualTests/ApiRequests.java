package com.company.TanyasManualTests;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApiRequests{
    private static PreparedStatement ps;
    private static ResultSet rs;
    public static void main(String[] args) {


//        manualTestCharacterSyntex();
        checkUserAddSyntax();

    }

    public static void manualTestCharacterSyntex(){
        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO character (char_id, char_name, total_sp, corp_id, ally_id) VALUES (?,?,?,?,?)");
            ps.setInt(1, 5);
            ps.setString(2,"Masha");
            ps.setLong(3, 1738730L);
            ps.setInt(4, 1426);
            ps.setInt(5, 15635);
            ps.execute();
        } catch  (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
            System.out.println("true");
        }

    }

    public static void checkUserAddSyntax(){
        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO characters (char_id, char_name, total_sp)" +
                    " VALUES (?,?,?)");
            ps.setInt(1, 74);
            ps.setString(2,"charName(userApi)");
            ps.setLong(3,56L);


            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
            System.out.println("false");
        }
    }
}
