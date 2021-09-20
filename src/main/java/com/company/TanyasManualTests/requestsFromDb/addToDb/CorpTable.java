package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.company.TanyasManualTests.dataTypes.AllyData.allyID;
import static com.company.TanyasManualTests.dataTypes.CharData.charID;
import static com.company.TanyasManualTests.dataTypes.CorpData.*;

public class CorpTable {
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void update(SsoApi api) throws ApiException {
        int id = 0;
        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM corporations WHERE corp_id = " + corpID(api));
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("corp_id");
            }

        } catch (Exception e) {
            //                           e.printStackTrace();
            OutputMessages.error();
            System.out.println("true");
        }

        if(id==corpID(api)){updateCorpData(api);}else{addNewCorp(api);}
    }

    public static void addNewCorp(SsoApi api){
        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO corporations " +
                    "(corp_id, corp_name, corp_ticker, member_count, ally_id) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, corpID(api));
            ps.setString(2, corpName(api));
            ps.setString(3, corpTicker(api));
            ps.setInt(4, memberCount(api));
            ps.setInt(5, allyID(api));
            ps.execute();
        }  catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

    public static void updateCorpData(SsoApi api){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE corporations " +
                    "SET member_count = ?, ally_id = ?  " +
                    "WHERE corp_id = " + charID(api));
            ps.setInt(1, memberCount(api));
            ps.setInt(2, allyID(api));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }

    }

}
