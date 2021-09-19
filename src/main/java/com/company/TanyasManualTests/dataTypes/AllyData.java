package com.company.TanyasManualTests.dataTypes;

import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.AllianceApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.model.AllianceResponse;

public class AllyData {
private static AllianceApi alliAPI = new AllianceApi();


    private static AllianceResponse allyRes(SsoApi api) throws ApiException {
       return alliAPI.getAlliancesAllianceId(CharData.charAffil(api).get(0).getAllianceId(), " ", null);
    }

    public static int allyID(SsoApi api) throws ApiException {
        return CharData.charAffil(api).get(0).getAllianceId();
    }

    public static String allyName (SsoApi api) throws ApiException{
        return allyRes(api).getName();
    }

    public static String allyTicker (SsoApi api) throws ApiException{
        return allyRes(api).getTicker();
    }

    public static String allyZKillLink(SsoApi api) throws ApiException {
        return "https://zkillboard.com/alliance/" + allyID(api) + "/";
    }

    public static String allyDotlanLink(SsoApi api) throws ApiException {
        return "https://evemaps.dotlan.net/alliance/" + allyID(api) + "/";
    }

    public static String allyLogoLink(SsoApi api, int size) throws ApiException {
        //available sizes 256, 128, 64
        return "https://images.evetech.net/alliances/" + allyID(api) + "/logo?size=" + size;
    }
}