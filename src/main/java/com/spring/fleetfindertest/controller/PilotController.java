package com.spring.fleetfindertest.controller;

import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
