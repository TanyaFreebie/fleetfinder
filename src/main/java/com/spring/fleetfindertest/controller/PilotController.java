package com.spring.fleetfindertest.controller;

import com.spring.fleetfindertest.API.AllyData;
import com.spring.fleetfindertest.API.CharData;
import com.spring.fleetfindertest.API.CorpData;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String homePage(Model model){
        List<Pilot> pilots = pilotService.findAll();
        model.addAttribute("pilots", pilots);
        return "pilot-list";
    }
    @GetMapping("/profile/")
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
            if(pilotService.findById((long) charID) == null) {
                System.out.println("SOUT CHAR: " + CharData.addCharacterDataToDb(userApi,accessToken));
                pilotService.savePilot(CharData.addCharacterDataToDb(userApi, accessToken));
            } else {
                String specialization = pilotService.findById((long) charID).getSpecialization();
                String areaOfOperations = pilotService.findById((long) charID).getArea();
                String timeZone = pilotService.findById((long) charID).getTimeZone();
                String advertText = pilotService.findById((long) charID).getAdvertText();
                System.out.println("SOUT CHAR: " + CharData.addCharacterDataToDb(userApi,accessToken,specialization,
                        areaOfOperations,timeZone,advertText));
                pilotService.savePilot(CharData.addCharacterDataToDb(userApi,accessToken,specialization,
                        areaOfOperations,timeZone,advertText));
            }
//            System.out.println("SOUT CHAR: " + CharData.addCharacterDataToDb(userApi,accessToken,specialization,
//                    areaOfOperations,timeZone,advertText));
//            pilotService.savePilot(CharData.addCharacterDataToDb(userApi,accessToken,specialization,
//                    areaOfOperations,timeZone,advertText));

            System.out.println("SOUT CORP: " + CorpData.addCorporationDataToDb(userApi,accessToken));
            corporationService.saveCorporation(CorpData.addCorporationDataToDb(userApi,accessToken));

            System.out.println("SOUT ALLIANCE: " + AllyData.addAllianceDataToDb(userApi,accessToken));
            if (AllyData.addAllianceDataToDb(userApi,accessToken) != null){
                System.out.println("SOUT ALLIANCE: " + AllyData.addAllianceDataToDb(userApi,accessToken));
                allianceService.saveAlliance(AllyData.addAllianceDataToDb(userApi, accessToken));
            }
            //allianceService.saveAlliance(AllianceTable.addAllianceDataToDb(userApi, accessToken));
        }
        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
        // return "index";
        // return "redirect:/profile/"+charId;
        List<Pilot> pilots = pilotService.findAll();
        model.addAttribute("pilots", pilots);
        return "redirect:/pilot-list";
    }
    //Pilots button in navbar
    @GetMapping("/pilot-list")
    public String pilotList(Model model){
        List<Pilot> pilots = pilotService.findAll();
        model.addAttribute("pilots", pilots);
        return "pilot-list";
    }
    //PILOT-LIST -> (Blue button) Profile
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
    //Profile button in navbar
    @GetMapping("profile")
    public String returnPilotPage(Model model){
        Pilot pilot = pilotService.findById((long) charId);
        model.addAttribute("pilot", pilot);
        //System.out.println("CHAR ID: " + charId);
        return "/profile";
    }
    //PILOT-LIST -> (Blue button) Advertisement
    @GetMapping("/pilot/{id}")
    public String getPilotAdvertisementFromList(@PathVariable("id") Integer id, Model model){
        Long longId = Long.valueOf(id);
        Pilot pilot = pilotService.findById(longId);
        model.addAttribute("pilot", pilot);
        return "/pilot";
    }
    //PROFILE (navbar) -> Create advertisement IN PROFILE
    @GetMapping("/create-advertisement")
    public String getPilotAdvertisement(Model model){
        Pilot pilot = pilotService.findById((long) charId);
        model.addAttribute("pilot", pilot);
        return "create-advertisement";
    }
    @PostMapping("/create-advertisement")
    public String createPilotAdvertisement(Pilot pilot){
        //Pilot pilot = pilotService.findById((long) charId);
        //model.addAttribute("pilot",pilot);
        System.out.println("PILOT: " + pilot.toString());
        pilotService.savePilot(pilot);
        return "redirect:/pilot-list";
    }
    ////PROFILE (navbar) -> Create advertisement IN PROFILE -> Create advertisement IN CREATE ADVERTISEMENT
//    @PostMapping("/create-advertisement")
//    public String savePilotAdvertisement(Model model){
//        Pilot pilot = pilotService.findById((long) charId);
//        model.addAttribute("pilot", pilot);
//        pilotService.savePilot(pilot);
//        System.out.println("PILOT ADVERT TEXT: " + pilot.getAdvertText());
//        return "redirect:/pilot-list";
//    }
//    @GetMapping("/add")
//    public String createPilot(Pilot pilot) throws ApiException {
////        User.addDataToDb();
////        System.out.println("PILOT: " + User.addDataToDb().toString());
////        pilotService.savePilot(User.addDataToDb());
////        return "redirect:/pilot-list";
//    }
}
