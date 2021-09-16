package com.company.helpers;

import com.spring.fleetfindertest.LoginController;

import net.troja.eve.esi.ApiException;

import net.troja.eve.esi.api.AllianceApi;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.CorporationApi;
import net.troja.eve.esi.model.*;

import java.util.Arrays;
import java.util.List;


public class User extends LoginController{


    public static CharacterInfo character() throws ApiException {
        CharacterInfo character = api.getCharacterInfo();
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

        charPortResp.getPx256x256();//character_portrait
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




        //запрос информации о алиансе
        if(charAffil.get(0).getAllianceId() != null){
        final AllianceApi alliAPI = new AllianceApi();

        final AllianceResponse AlliRes = alliAPI.getAlliancesAllianceId(charAffil.get(0).getAllianceId(), datasource, null);
        System.out.println(AlliRes.getName());}

    }






}
