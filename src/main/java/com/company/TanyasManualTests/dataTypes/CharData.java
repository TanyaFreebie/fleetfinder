package com.company.TanyasManualTests.dataTypes;

import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.SkillsApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.model.CharacterAffiliationResponse;
import net.troja.eve.esi.model.CharacterInfo;
import net.troja.eve.esi.model.CharacterSkillsResponse;

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

  public static int charID(SsoApi api) throws ApiException{
        return charInfo(api).getCharacterID();
  }

  public static String charName(SsoApi api) throws ApiException{
        return charInfo(api).getCharacterName();
  }

  public static List <CharacterAffiliationResponse> charAffil(SsoApi api) throws ApiException{
      List charList= Arrays.asList(charID(api));
        return   charAPI.postCharactersAffiliation(charList, datasource);
  }

  public static String charPortraitLink(SsoApi api, int size) throws ApiException{
      //available sizes 256, 128, 64
        return "https://images.evetech.net/characters/"+charID(api)+"/portrait?size=" + size;
  }

  public static long charTotalSkillPoints(SsoApi api, String accessToken) throws ApiException{
      final CharacterSkillsResponse skillResp = skillApi.getCharactersCharacterIdSkills(charID(api), datasource, null,accessToken);
      return skillResp.getTotalSp();

  }

  public  static String zKillLink(SsoApi api) throws ApiException{
        return "https://zkillboard.com/character/"+charID(api)+"/";
  }

  //END OF CLASS

}
