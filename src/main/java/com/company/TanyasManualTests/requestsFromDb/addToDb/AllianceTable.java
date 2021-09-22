package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.spring.fleetfinder.API.AllyData.*;

public class AllianceTable {
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void update(SsoApi api) throws ApiException {
        if (allyID(api) != 0) {

            int id = 0;
            try {
                ps = DbConnection.user().prepareStatement("SELECT * FROM alliances WHERE ally_id = " + allyID(api));
                rs = ps.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("ally_id");
                }

            } catch (Exception e) {
                //                           e.printStackTrace();
                OutputMessages.error();
                System.out.println("true");
            }
            if (id != allyID(api)) {
                addNewAlly(api);
            }
        }
    }

    public static void addNewAlly(SsoApi api){
        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO alliances " +
                    "(ally_id, ally_name, ally_ticker) " +
                    "VALUES (?, ?, ?)");
            ps.setInt(1, allyID(api));
            ps.setString(2, allyName(api));
            ps.setString(3, allyTicker(api));
            ps.execute();
        }  catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

}
