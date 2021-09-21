package com.spring.fleetfindertest.controller;

import com.company.TanyasManualTests.dataTypes.CharData;
import com.spring.fleetfindertest.model.Auth;
import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.service.PilotService;
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
public class PilotController {
    //Дёргаем данные из ДБ
    private final PilotService pilotService;
    protected static SsoApi userApi;
    protected static String accessToken;
    private static int charId;
    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping("/")
    //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
    public String index(@RequestParam(name = "code", required = false) String authCode, @RequestParam(name = "state", required = false) String authState, Model model) throws ApiException {
        charId = 0;
        if (authCode != null) {
            final String ClientId = Auth.get().getClientId();

            final OAuth auth = Auth.get();
            auth.finishFlow(authCode, authState, authState);

            accessToken = auth.getAccessToken();
            String refreshToken = auth.getRefreshToken();
            model.addAttribute("code", refreshToken);
            final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(refreshToken).build();

            userApi = new SsoApi(userClient);


            //запрос имени для приветствия
            model.addAttribute("name", CharData.charName(userApi));
//+++TEST++++
            int charID = CharData.charID(userApi);
            System.out.println(charID);
        }
        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
        //return "index";
        // return "redirect:/profile/"+charId;
        return "index";
    }
    @GetMapping("/pilot-list")
    public String pilotList(Model model){
        List<Pilot> pilots = pilotService.findAll();
        model.addAttribute("pilots", pilots);
        return "pilot-list";
    }
    @GetMapping("/profile/{id}")
    public String getPilot(@PathVariable("id") Integer id, Model model){
        //I need to convert int value of Pilot`s Id to Long -> So I make id as String and then change id to Long
        System.out.println("!!!!!!!!!!!!!!!!!!!ID!!!!!!!!!!!!!!!!!");
        Long longId = Long.valueOf(id);
        System.out.println(longId);
        Pilot pilot = pilotService.findById(longId);
        System.out.println("!!!!!!!!!!!!!!!!!!!PILOT!!!!!!!!!!!!!!!!!");
        pilot.toString();
        model.addAttribute("pilot", pilot);
        return "/profile";
    }
    @GetMapping("profile")
    public String returnPilotPage(Model model){
        Pilot pilot = pilotService.findById((long) charId);
        model.addAttribute("pilot", pilot);
        //System.out.println("CHAR ID: " + charId);
        return "/profile";
    }
    @GetMapping("/add")
    public String createPilot(Pilot pilot) throws ApiException {
        CharData.updateChar(userApi, accessToken);
        System.out.println("PILOT: " + CharData.updateChar(userApi, accessToken));
        pilotService.savePilot(CharData.updateChar(userApi, accessToken));
        return "redirect:/pilot-list";

    }
}
