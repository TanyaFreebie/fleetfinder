package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.TanyasManualTests.dataTypes.AllyData;
import com.company.TanyasManualTests.dataTypes.CharData;
import com.company.TanyasManualTests.dataTypes.CorpData;
import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import net.troja.eve.esi.api.SsoApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Character {
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void addNewChar(SsoApi api, String accessToken){

        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO character (char_id, char_name, total_sp, corp_id, ally_id) VALUES (?,?,?,?,?)");
            ps.setInt(1, CharData.charID(api));
            ps.setString(2,CharData.charName(api));
            ps.setLong(3,CharData.charTotalSkillPoints(api, accessToken));
            ps.setInt(4, CorpData.corpID(api));
            ps.setInt(5, AllyData.allyID(api));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
            System.out.println("false");
        }
    }

    public static void updateUserData(SsoApi api, String accessToken){

    }
}
