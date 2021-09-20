package com.company.helpers;

import com.company.dbHelper.dbConnection.DbConnection;
import com.spring.fleetfindertest.controller.LoginController;

import com.spring.fleetfindertest.model.Pilot;
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
    public static CharacterInfo character() throws ApiException {
        CharacterInfo character = userApi.getCharacterInfo();
        return character;
    }

    public static Pilot addDataToDb() throws ApiException {
        Pilot pilot = new Pilot();
        //запрос информации о пилоте
        int charID = character().getCharacterID();
        List charList = Arrays.asList(charID);
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
        if (charAffil.get(0).getAllianceId() != null) {
            final AllianceApi alliAPI = new AllianceApi();

            final AllianceResponse AlliRes = alliAPI.getAlliancesAllianceId(charAffil.get(0).getAllianceId(), datasource, null);
            allyID = charAffil.get(0).getAllianceId();
            allyName = AlliRes.getName();
        }
//        pilot.setCharId(charID);
//        pilot.setCharName(name);
//        pilot.setCharImage(charImage);
//        pilot.setCorpId(corpID);
//        pilot.setCorpName(nameCorp);
//        pilot.setAllyId(allyID);
//        pilot.setAllyName(allyName);
        return pilot;

    }

}
