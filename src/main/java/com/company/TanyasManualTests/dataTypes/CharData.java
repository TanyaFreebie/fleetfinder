package com.company.TanyasManualTests.dataTypes;

import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.SkillsApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.model.*;

import java.util.Arrays;
import java.util.List;

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

    public static String corpProfileAccess(SsoApi api, String accessToken) throws ApiException{
        String answer;
        final CharacterRolesResponse  charRoles = charAPI.getCharactersCharacterIdRoles(charID(api), datasource, null, accessToken);

        return answer = "";
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


    //НЕ ГОТОВО надо создать чара и затестить - это для создания
    // страничек корп и алиансев и генерации профилей со стороны юзера

    public static String cropRole(SsoApi api)throws ApiException{
        final CharacterRolesResponse charRoles;
        return " ";
    }

    //END OF CLASS

}
