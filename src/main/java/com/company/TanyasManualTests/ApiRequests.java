package com.company.TanyasManualTests;

import com.company.dbHelper.dbConnection.DbConnection;
import com.spring.fleetfindertest.controller.LoginController;

public class ApiRequests extends LoginController{

    public static void main(String[] args) {
        DbConnection.user();
    }
}
