package com.spring.fleetfindertest.service;

import com.spring.fleetfindertest.model.Alliance;
import com.spring.fleetfindertest.repository.AllianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllianceService {
    private final AllianceRepository allianceRepository;

    @Autowired
    public AllianceService(AllianceRepository allianceRepository) {
        this.allianceRepository = allianceRepository;
    }

    public Alliance saveAlliance(Alliance alliance){
        return allianceRepository.save(alliance);
    }
}
