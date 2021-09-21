package com.company.TanyasManualTests;

import com.company.TanyasManualTests.requestsFromDb.addToDb.AdvertTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApiRequests{
    private static PreparedStatement ps;
    private static ResultSet rs;
    public static void main(String[] args) {


//        manualTestCharacterSyntex();

            AdvertTable.status(96069947, false);

    }


}
