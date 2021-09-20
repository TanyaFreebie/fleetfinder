package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdvertTable {

    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void author(int authorID) {
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

    public static void advertText(int authorID, String text){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE advertisements " +
                    "SET advert_text = ?  " +
                    "WHERE author_id = " + authorID);
            ps.setString(1, text);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }

    }

    public static void specialization(int authorID, String text){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE advertisements " +
                    "SET specialization = ?  " +
                    "WHERE author_id = " + authorID);
            ps.setString(1, text);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

    public static void timezone(int authorID, String text){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE advertisements " +
                    "SET timezone = ?  " +
                    "WHERE author_id = " + authorID);
            ps.setString(1, text);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

    public static void area(int authorID, String text){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE advertisements " +
                    "SET area = ?  " +
                    "WHERE author_id = " + authorID);
            ps.setString(1, text);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

    public static boolean status(int authorID, boolean active){

        try {
            ps = DbConnection.user().prepareStatement("UPDATE advertisements " +
                    "SET is_active = ?  " +
                    "WHERE author_id = " + authorID);
            ps.setBoolean(1, active);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }

        return active;
    }


}
