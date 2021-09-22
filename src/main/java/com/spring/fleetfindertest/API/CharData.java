package com.spring.fleetfindertest.API;

import com.spring.fleetfindertest.model.Pilot;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.SkillsApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.model.*;

import java.util.Arrays;
import java.util.List;

import static com.spring.fleetfindertest.API.AllyData.allyID;
import static com.spring.fleetfindertest.API.CorpData.corpID;

public class CharData {
    private static String datasource = "";
    private static CharacterApi charAPI = new CharacterApi();
    private static SkillsApi skillApi = new SkillsApi();


    public static CharacterInfo charInfo(SsoApi api) throws ApiException {
        CharacterInfo character = api.getCharacterInfo();
        return character;
    }

    public static int charID(SsoApi api) throws ApiException {
        return charInfo(api).getCharacterID();
    }

    public static String charName(SsoApi api) throws ApiException {
        return charInfo(api).getCharacterName();
    }

    public static List<CharacterAffiliationResponse> charAffil(SsoApi api) throws ApiException {
        List charList = Arrays.asList(charID(api));
        return charAPI.postCharactersAffiliation(charList, datasource);
    }

    public static long charTotalSkillPoints(SsoApi api, String accessToken) throws ApiException {
        final CharacterSkillsResponse skillResp = skillApi.getCharactersCharacterIdSkills(charID(api), datasource, null, accessToken);
        return skillResp.getTotalSp();
    }


    //этот метод нужен для кнопки создания корпы
    // если он "true", то кнопка есть
    // если "false", то кнопки нет
    public static boolean corpProfileAccess(SsoApi api, String accessToken) throws ApiException{

        final CharacterRolesResponse  charRoleResp = charAPI.getCharactersCharacterIdRoles(charID(api), datasource, null, accessToken);
        List<CharacterRolesResponse.RolesEnum> roles = charRoleResp.getRoles();
        boolean hasAccess = roles.toString().contains("Director");

        return hasAccess;

    }

    public static String charZKillLink(SsoApi api) throws ApiException {
        return "https://zkillboard.com/character/" + charID(api) + "/";
    }

    public static String eveWhoLink(SsoApi api) throws ApiException {
        return "https://evewho.com/character/" + charID(api);
    }

    public static String charPortraitLink(SsoApi api, int size) throws ApiException {
        //available sizes 256, 128, 64
        return "https://images.evetech.net/characters/" + charID(api) + "/portrait?size=" + size;
    }


public static Pilot updateChar(SsoApi api, String accessToken) throws ApiException{
        Pilot character = new Pilot();
        character.setCharId((long) charID(api));
        character.setCharName(charName(api));
        character.setTotalSp(charTotalSkillPoints(api, accessToken));
        character.setCorpId(corpID(api));
        character.setCorpAccess(corpProfileAccess(api, accessToken));
        character.setAllyId(allyID(api));
        return character;
    }
    //END OF CLASS

}
