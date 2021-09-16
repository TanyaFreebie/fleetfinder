package com.spring.fleetfindertest;

import com.company.helpers.Auth;
import com.company.helpers.User;
import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.AllianceApi;
import net.troja.eve.esi.api.CharacterApi;
import net.troja.eve.esi.api.CorporationApi;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.auth.SsoScopes;
import net.troja.eve.esi.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Вызываем контроллер который обрабатывает конкретный запрос в браузере
@Controller
public class LoginController {
    protected SsoApi api;
    protected String accessToken;
    protected String refreshToken;
    protected int charID;


    //GetMapping указывает по какому URL и какой HTTP запрос мы хотим обработать. Может быть например @PostMapping("/submit") или что то типа
    @GetMapping("/")

    //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
    public String index(@RequestParam(name = "code", required = false) String authCode, @RequestParam(name = "state", required = false) String authState, Model model) throws ApiException {

        if (authCode != null) {
            final String ClientId = Auth.get().getClientId();

            final OAuth auth = Auth.get();
            auth.finishFlow(authCode, authState, authState);

            accessToken = auth.getAccessToken();
            refreshToken = auth.getRefreshToken();
            model.addAttribute("code", refreshToken);
            final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(refreshToken).build();

            api = new SsoApi(userClient);

// получение информации от сервера EVE online
            CharacterInfo character = api.getCharacterInfo();
            charID = character.getCharacterID();
            //запрос имени для приветствия
            model.addAttribute("name", character.getCharacterName());

            //запрос информации о пилоте
            List charList= Arrays.asList(charID);
            String datasource = "";

            final CharacterApi charAPI = new CharacterApi();
            final List<CharacterAffiliationResponse> charAffil = charAPI.postCharactersAffiliation(charList, datasource);
            System.out.println(charAffil.get(0));
            //портрет
            final CharacterPortraitResponse charPortResp = charAPI.getCharactersCharacterIdPortrait(charID, datasource, null);
            System.out.println(charPortResp);
            //запрос информации о корпорации
            final CorporationApi corpAPI = new CorporationApi();
            final CorporationResponse corpRes = corpAPI.getCorporationsCorporationId(charAffil.get(0).getCorporationId(), datasource, null);
            System.out.println(corpRes);

            //запрос информации о алиансе
            final AllianceApi alliAPI = new AllianceApi();
            final AllianceResponse AlliRes = alliAPI.getAlliancesAllianceId(charAffil.get(0).getAllianceId(), datasource, null);
            System.out.println(AlliRes);





        }

        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
        return "index";
    }

    @GetMapping("/login")

    public String login() {

        String state = "d2NTpYVRNy2jnnFjQXgGdIHTp5gJexNZqWlHP9Zn";

        final OAuth auth = Auth.get();

        final Set<String> scopes = new HashSet<>();
        scopes.add(SsoScopes.PUBLIC_DATA);
        scopes.add(SsoScopes.ESI_SKILLS_READ_SKILLS_V1);
        scopes.add(SsoScopes.ESI_CHARACTERS_READ_CORPORATION_ROLES_V1);


        String redirectUri;
        if (System.getenv().get("SSO_CALLBACK_URL") != null) {
            redirectUri = System.getenv().get("SSO_CALLBACK_URL");
        } else {
            redirectUri = "http://localhost:8080/";
        }

        return "redirect:"+auth.getAuthorizationUri(redirectUri, scopes, state);
    }



}