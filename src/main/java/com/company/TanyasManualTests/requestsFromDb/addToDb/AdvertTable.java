package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdvertTable {

    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void update(int authorID) {
        int id = 0;
        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM advertisements WHERE author_id = " + authorID);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("author_id");
            }

        } catch (Exception e) {
            //                           e.printStackTrace();
            OutputMessages.error();
            System.out.println("true");
        }

        if(id!=authorID){addNewAdvert(authorID);}
    }

    public static void addNewAdvert(int authorID){
        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO advertisements " +
                    "(author_id) " +
                    "VALUES (?)");
            ps.setInt(1, authorID);
            ps.execute();
        }  catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

//date format method in case we will need timestamps
    public static String dateFormat() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }
}
