package com.spring.fleetfindertest.API;

import com.spring.fleetfindertest.model.Corporation;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.CorporationApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.model.CorporationResponse;

import static com.spring.fleetfinder.API.AllyData.allyID;

public class CorpData{
    private static String datasource = "";
    public static CorporationApi corpAPI = new CorporationApi();

    private static  CorporationResponse corpRes(SsoApi api) throws ApiException {
       return corpAPI.getCorporationsCorporationId(CharData.charAffil(api).get(0).getCorporationId(), datasource, null);
    }

    public static int corpID(SsoApi api) throws ApiException {
        return CharData.charAffil(api).get(0).getCorporationId();
    }

    public static String corpName (SsoApi api) throws ApiException{
        return corpRes(api).getName();
    }

    public static String corpTicker (SsoApi api) throws ApiException{
        return corpRes(api).getTicker();
    }

    public static int memberCount (SsoApi api) throws  ApiException{
        return corpRes(api).getMemberCount();
    }

    public static String corpZKillLink(SsoApi api) throws ApiException {
        return "https://zkillboard.com/corporation/" + corpID(api) + "/";
    }

    public static String corpLogoLink(SsoApi api, int size) throws ApiException {
        //available sizes 256, 128, 64
        return "https://images.evetech.net/corporations/" + corpID(api) + "?size=" + size;
    }

    public static String eveWhoLink(SsoApi api) throws ApiException {
        return "https://evewho.com/corporation/" + corpID(api);
    }

    public static Corporation updateCorp(SsoApi api) throws ApiException{
        Corporation corp = new Corporation();
        corp.setCorpId((long)corpID(api));
        corp.setCorpName(corpName(api));
        corp.setCorpTicker(corpTicker(api));
        corp.setMemberCount(memberCount(api));
        corp.setAllyId(allyID(api));



        return corp;
    }
}
