package com.spring.fleetfindertest.controller;

import com.spring.fleetfindertest.model.Auth;
import com.company.helpers.User;
import com.spring.fleetfindertest.model.Pilot;
import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Вызываем контроллер который обрабатывает конкретный запрос в браузере
@Controller
public class LoginController {
    protected static SsoApi api;
    // wwww.evewho.com/character/CharId
    //GetMapping указывает по какому URL и какой HTTP запрос мы хотим обработать. Может быть например @PostMapping("/submit") или что то типа
    @GetMapping("/")
    //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
    public String index(@RequestParam(name = "code", required = false) String authCode, @RequestParam(name = "state", required = false) String authState, Model model) throws ApiException {

        if (authCode != null) {
            final String ClientId = Auth.get().getClientId();
            final OAuth auth = Auth.get();
            auth.finishFlow(authCode, authState, authState);
            auth.getAccessToken();
            String refreshToken = auth.getRefreshToken();
            model.addAttribute("code", refreshToken);
            final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(refreshToken).build();

            api = new SsoApi(userClient);

            // получение информации от сервера EVE online
            CharacterInfo character = api.getCharacterInfo();
            int charID = character.getCharacterID();
            //запрос имени для приветствия
            model.addAttribute("name", character.getCharacterName());
            User.addDataToDb();
        }
        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
        //return "index";
        return "redirect:/pilot-list";
    }
}