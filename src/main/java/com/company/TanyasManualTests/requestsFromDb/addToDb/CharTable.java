package com.company.TanyasManualTests.requestsFromDb.addToDb;

import com.company.TanyasManualTests.dataTypes.AllyData;
import com.company.dbHelper.dbConnection.DbConnection;
import com.company.helpers.OutputMessages;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.TanyasManualTests.dataTypes.CharData.*;
import static com.company.TanyasManualTests.dataTypes.CorpData.corpID;



public class CharTable {
    private static PreparedStatement ps;
    private static ResultSet rs;
//надо пересмотреть этот метод
    public static void update(SsoApi api, String accessToken) throws ApiException {
        int id = 0;
        System.out.println("null id: "+id);
        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM characters WHERE char_id = " + charID(api));
            rs = ps.executeQuery();
            System.out.println("try id: "+id);
            while (rs.next()) {
                id = rs.getInt("char_id");
                System.out.println("while id: "+ id);
            }

        } catch (Exception e) {
                //                           e.printStackTrace();
                OutputMessages.error();
                System.out.println("true");
            }
        System.out.println("id after while: " +id);
        if(id==charID(api)){updateCharData(api, accessToken);}else{addNewChar(api,accessToken);}
    }

    public static void addNewChar(SsoApi api, String accessToken){

        try {
            ps = DbConnection.user().prepareStatement("INSERT INTO characters (char_id, char_name, total_sp, corp_id, ally_id) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, charID(api));
            ps.setString(2,charName(api));
            ps.setLong(3, charTotalSkillPoints(api, accessToken));
            ps.setInt(4, corpID(api));
            ps.setInt(5, AllyData.allyID(api));
            ps.execute();
        } catch  (SQLException | ApiException throwables) {
//                    throwables.printStackTrace();
            OutputMessages.error();
            System.out.println("addNewChar");
        }
    }

    public static void updateCharData(SsoApi api, String accessToken){
        try {
            ps = DbConnection.user().prepareStatement("UPDATE characters SET total_sp = ?, corp_id = ?, ally_id = ?  WHERE char_id = " + charID(api));
            ps.setLong(1, charTotalSkillPoints(api, accessToken));
            ps.setInt(2, corpID(api));
            ps.setInt(3, AllyData.allyID(api));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            OutputMessages.error();
            System.out.println("false");
        }
    }
}
