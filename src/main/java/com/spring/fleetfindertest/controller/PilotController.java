package com.spring.fleetfindertest.controller;

import com.company.helpers.User;
import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.service.PilotService;
import net.troja.eve.esi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/add")
    public String createPilot(Pilot pilot) throws ApiException {
        User.addDataToDb();
        System.out.println("PILOT: " + User.addDataToDb().toString());
        pilotService.savePilot(User.addDataToDb());
        return "redirect:/pilot-list";
    }
}
