package com.spring.fleetfindertest.controller;

import com.company.helpers.User;
import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.service.PilotService;
import net.troja.eve.esi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PilotController {
    //Дёргаем данные из ДБ
    private final PilotService pilotService;

    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping("/profile")
    public String profile(Model model){

        return "profile";
    }
    @GetMapping("/pilot-list")
    public String pilotList(Model model){
        List<Pilot> pilots = pilotService.findAll();
        model.addAttribute("pilots", pilots);
        return "pilot-list";
    }
    @GetMapping("profile/{id}")
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
//    @GetMapping("/add")
//    public String createPilot(Pilot pilot) throws ApiException {
//        User.addDataToDb();
//        System.out.println("PILOT: " + User.addDataToDb().toString());
//        pilotService.savePilot(User.addDataToDb());
//        return "redirect:/pilot-list";
//    }
}
