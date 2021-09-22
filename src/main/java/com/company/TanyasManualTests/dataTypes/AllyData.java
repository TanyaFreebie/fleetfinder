package com.company.TanyasManualTests.dataTypes;

import com.spring.fleetfindertest.model.Alliance;
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
        int allyID = 0;
        if(  CharData.charAffil(api).get(0).getAllianceId()!= null){
            allyID =CharData.charAffil(api).get(0).getAllianceId();}
        return allyID;
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

//    public static Alliance updateAlly(SsoApi api) throws ApiException{
//     Alliance ally = new Alliance();
//     ally.setAllyId((long) allyID(api));
//     ally.setAllyName(allyName(api));
//     ally.setAllyTicker(allyTicker(api));
//
//        return ally;
//    }
}
