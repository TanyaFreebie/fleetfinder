package com.company.helpers;

import com.spring.fleetfindertest.LoginController;

import net.troja.eve.esi.ApiException;

import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.model.CharacterCorporationHistoryResponse;
import net.troja.eve.esi.model.CharacterInfo;

import java.util.List;


public class User extends LoginController{


    public CharacterInfo character() throws ApiException {
        CharacterInfo character = api.getCharacterInfo();
        return character;
    }








}
