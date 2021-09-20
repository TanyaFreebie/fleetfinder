package com.spring.fleetfindertest.service;

import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.repository.PilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PilotService {

    private final PilotRepository pilotRepository;

    @Autowired
    public PilotService(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    public Pilot findById(Long id){
        return pilotRepository.findById(id).orElse(null);
    }
    public List<Pilot> findAll(){
        return pilotRepository.findAll();
    }
    public Pilot savePilot(Pilot pilot){
        return pilotRepository.save(pilot);
    }
}
