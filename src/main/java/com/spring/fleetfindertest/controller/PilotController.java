package com.spring.fleetfindertest.controller;

import com.company.TanyasManualTests.dataTypes.CharData;

import com.company.TanyasManualTests.requestsFromDb.addToDb.AllianceTable;
import com.company.TanyasManualTests.requestsFromDb.addToDb.CharTable;
import com.company.TanyasManualTests.requestsFromDb.addToDb.CorpTable;
import com.spring.fleetfindertest.model.Auth;
import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.service.AllianceService;
import com.spring.fleetfindertest.service.CorporationService;
import com.spring.fleetfindertest.service.PilotService;
import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PilotController {
    //Дёргаем данные из БД
    private final PilotService pilotService;
    private final CorporationService corporationService;
    private final AllianceService allianceService;

    protected static SsoApi userApi;
    protected static String accessToken;
    private static int charId;
    @Autowired
    public PilotController(PilotService pilotService, CorporationService corporationService, AllianceService allianceService) {
        this.pilotService = pilotService;
        this.corporationService = corporationService;
        this.allianceService = allianceService;
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
            charId = charID;
//            AdvertTable.author(charID);
//            AdvertTable.advertText(charID, "looking for new corpmates");
//            AdvertTable.specialization(charID, "pvp");
//            AdvertTable.timezone(charID, "Asia");
//            AdvertTable.area(charID, "Null");
//            AdvertTable.status(charID, true);
            System.out.println("SOUT CHAR: " + CharTable.addCharacterDataToDb(userApi,accessToken));
            pilotService.savePilot(CharTable.addCharacterDataToDb(userApi,accessToken));

            System.out.println("SOUT CORP: " + CorpTable.addCorporationDataToDb(userApi,accessToken));
            corporationService.saveCorporation(CorpTable.addCorporationDataToDb(userApi,accessToken));

            System.out.println("SOUT ALLIANCE: " + AllianceTable.addAllianceDataToDb(userApi,accessToken));
            allianceService.saveAlliance(AllianceTable.addAllianceDataToDb(userApi,accessToken));
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
//    @GetMapping("/add")
//    public String createPilot(Pilot pilot) throws ApiException {
////        User.addDataToDb();
////        System.out.println("PILOT: " + User.addDataToDb().toString());
////        pilotService.savePilot(User.addDataToDb());
////        return "redirect:/pilot-list";

//    }
}
