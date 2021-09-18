package com.company.helpers;

import com.company.dbHelper.dbConnection.DbConnection;
import com.spring.fleetfindertest.controller.LoginController;

import net.troja.eve.esi.ApiException;

import net.troja.eve.esi.api.AllianceApi;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.CorporationApi;
import net.troja.eve.esi.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class User extends LoginController{
    private static PreparedStatement ps;
    private static ResultSet rs;


    public static CharacterInfo character() throws ApiException {
        CharacterInfo character = userApi.getCharacterInfo();
        return character;
    }

    public static void addDataToDb() throws ApiException{
        //запрос информации о пилоте
        int charID = character().getCharacterID();
        List charList= Arrays.asList(charID);
        String datasource = "";

        final CharacterApi charAPI = new CharacterApi();
        final List<CharacterAffiliationResponse> charAffil = charAPI.postCharactersAffiliation(charList, datasource);
        System.out.println(charAffil.get(0));
        //портрет
        final CharacterPortraitResponse charPortResp = charAPI.getCharactersCharacterIdPortrait(charID, datasource, null);
        System.out.println("\n" + charPortResp.getPx256x256());

        String charImage = charPortResp.getPx256x256();//character_portrait
        String name = character().getCharacterName();
        System.out.println(name);//character_name
        System.out.println(charID);// character_id
        //generate evewho and zkillbord links

        //запрос информации о корпорации
        final CorporationApi corpAPI = new CorporationApi();
        final CorporationResponse corpRes = corpAPI.getCorporationsCorporationId(charAffil.get(0).getCorporationId(), datasource, null);
        int corpID = charAffil.get(0).getCorporationId();//corporation_id
        String nameCorp = corpRes.getName();//corporation_name
        System.out.println("\n" + corpID);
        System.out.println(nameCorp);
        String allyName = " ";
        int allyID = 0;

        //запрос информации о алиансе
        if(charAffil.get(0).getAllianceId() != null){
            final AllianceApi alliAPI = new AllianceApi();

            final AllianceResponse AlliRes = alliAPI.getAlliancesAllianceId(charAffil.get(0).getAllianceId(), datasource, null);
            allyID = charAffil.get(0).getAllianceId();
            allyName = AlliRes.getName();
        }

        try {
            ps = DbConnection.user().prepareStatement("SELECT * FROM test WHERE char_id = " + charID);
            rs = ps.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("char_id");
                System.out.println("Id from database:" + id);
                String idString= Integer.toString(rs.getInt("char_id"));
                System.out.println("id from database " +id);
                if (id == charID) {
                    try {
                        ps = DbConnection.user().prepareStatement("UPDATE test SET corp_name = ?, corp_id = ?, ally_id = ?, ally_name = ? WHERE char_id = " + charID);
                        ps.setString(1,nameCorp);
                        ps.setInt(2,corpID);
                        ps.setInt(3,allyID);
                        ps.setString(4,allyName);
                        ps.execute();
                    } catch (Exception e) {
                        //                           e.printStackTrace();
                        OutputMessages.error();
                        System.out.println("true");
                    }
                }
            }
            if(id ==0){
                System.out.println("hardcoded check id " + id);
                System.out.println("retrived id " + charID);
                try {
                    ps = DbConnection.user().prepareStatement("INSERT INTO test (char_id, char_name, char_image, corp_id, corp_name, ally_id, ally_name)" +
                            " VALUES (?,?,?,?,?,?,?)");
                    ps.setInt(1, charID);
                    ps.setString(2,name);
                    ps.setString(3,charImage);
                    ps.setInt(4,corpID);
                    ps.setString(5,nameCorp);
                    ps.setInt(6,allyID);
                    ps.setString(7,allyName);
                    ps.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    OutputMessages.error();
                    System.out.println("false");
                }
            }
        } catch (SQLException throwables) {
//                    throwables.printStackTrace();
            OutputMessages.error();
            System.out.println("global");

        }

    }


}
