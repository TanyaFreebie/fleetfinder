package com.company.helpers;

import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;

public class Auth {
    static final String ClientId = "be64b8e2b18d408a9202fa8f27173d55";
    static  String CharacterID;
    public static OAuth get(){


        final ApiClient client;
        client = new ApiClientBuilder().clientID(ClientId).build();

        final OAuth auth = (OAuth) client.getAuthentication("evesso");
        return auth;
    }



}
