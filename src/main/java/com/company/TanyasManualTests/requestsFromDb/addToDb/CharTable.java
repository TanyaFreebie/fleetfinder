package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.spring.fleetfinder.API.AllyData;
import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import com.spring.fleetfinder.model.Pilot;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.spring.fleetfinder.API.CharData.*;
import static com.spring.fleetfinder.API.CorpData.corpID;


public class CharTable {
    private static PreparedStatement ps;
    private static ResultSet rs;
    //надо пересмотреть этот метод
    public static Pilot addDataToDb(SsoApi api, String accessToken) throws ApiException {
        Pilot pilot = new Pilot();
        pilot.setCharId((long) charID(api));
        pilot.setCharName(charName(api));
        pilot.setCorpId(corpID(api));
        pilot.setCorpAccess(corpProfileAccess(api, accessToken));
        pilot.setAllyId(AllyData.allyID(api));
        pilot.setTotalSp(charTotalSkillPoints(api, accessToken));
        //pilot.setLastUpdate();
        return pilot;
    }
    public static void update(SsoApi api, String accessToken) throws ApiException {
        int id = 0;
        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM characters WHERE char_id = " + charID(api));
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("char_id");
            }

        } catch (Exception e) {
                //                           e.printStackTrace();
                OutputMessages.error();
                System.out.println("true");
            }

        if(id==charID(api)){updateCharData(api, accessToken);}else{addNewChar(api,accessToken);}
    }

    public static void addNewChar(SsoApi api, String accessToken){

        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO characters " +
                    "(char_id, char_name, total_sp, corp_id, ally_id, corp_access)" +
                    " VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, charID(api));
            ps.setString(2,charName(api));
            ps.setLong(3, charTotalSkillPoints(api, accessToken));
            ps.setInt(4, corpID(api));
            ps.setInt(5, AllyData.allyID(api));
            ps.setBoolean(6, corpProfileAccess(api, accessToken));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }

    public static void updateCharData(SsoApi api, String accessToken){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE characters " +
                    "SET total_sp = ?, corp_id = ?, ally_id = ?, corp_access = ?  " +
                    "WHERE char_id = " + charID(api));
            ps.setLong(1, charTotalSkillPoints(api, accessToken));
            ps.setInt(2, corpID(api));
            ps.setInt(3, AllyData.allyID(api));
            ps.setBoolean(4, corpProfileAccess(api, accessToken));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
        }
    }
}
