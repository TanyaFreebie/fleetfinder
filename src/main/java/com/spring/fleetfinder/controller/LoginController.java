package com.spring.fleetfinder.controller;

//Вызываем контроллер который обрабатывает конкретный запрос в браузере

import com.spring.fleetfinder.API.CharData;


import com.spring.fleetfinder.model.Pilot;
import com.spring.fleetfinder.service.PilotService;

import com.spring.fleetfinder.model.Auth;

import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class LoginController {
    protected static SsoApi userApi;
    protected static String accessToken;
    protected static String refreshToken;

// wwww.evewho.com/character/CharId

    //GetMapping указывает по какому URL и какой HTTP запрос мы хотим обработать. Может быть например @PostMapping("/submit") или что то типа
    @GetMapping("/profile/")
    //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
    public String profile(@RequestParam(name = "code", required = false) String authCode, @RequestParam(name = "state", required = false) String authState, Model model) throws ApiException {
        if (authCode != null) {

            //
            final String ClientId = Auth.get().getClientId();
            final OAuth auth = Auth.get();
            auth.finishFlow(authCode, authState, authState);
            accessToken = auth.getAccessToken();

            refreshToken = auth.getRefreshToken();
            model.addAttribute("code", refreshToken);
            final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(refreshToken).build();

            userApi = new SsoApi(userClient);


            //запрос имени для приветствия
            model.addAttribute("name", CharData.charName(userApi));
//+++TEST++++
            int charID = CharData.charID(userApi);
            System.out.println(charID);
//            AdvertTable.author(charID);
//            AdvertTable.advertText(charID, "looking for new corpmates");
//            AdvertTable.specialization(charID, "pvp");
//            AdvertTable.timezone(charID, "Asia");
//            AdvertTable.area(charID, "Null");
//            AdvertTable.status(charID, true);

        }
        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю

       // return "redirect:/profile/
        return "profile";

    }

//    @GetMapping("/profile/{id}")

}
