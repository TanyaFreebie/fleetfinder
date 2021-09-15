package com.company.helpers;

import com.spring.fleetfindertest.LoginController;

import net.troja.eve.esi.ApiException;

import net.troja.eve.esi.model.CharacterInfo;

public class Character {


    public static CharacterInfo character() throws ApiException {
        CharacterInfo character = LoginController.api.getCharacterInfo();

        character.getCharacterID();
        character.getCharacterName();




        return character;
    }


}
