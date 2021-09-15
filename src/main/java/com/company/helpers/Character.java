package com.company.helpers;

import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.model.CharacterInfo;

public class Character {


    public static CharacterInfo characterInfo() {
        final String ClientId = Auth.get().getClientId();

        final OAuth auth = Auth.get();


        final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(auth.getRefreshToken()).build();

        System.out.println(auth.getRefreshToken());
        System.out.println(auth.getAccessToken());

        final SsoApi api = new SsoApi(userClient);

        CharacterInfo character = null;
        try {
            character = api.getCharacterInfo();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return character;
    }


}
