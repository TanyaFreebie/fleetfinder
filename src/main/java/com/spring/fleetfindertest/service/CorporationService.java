package com.spring.fleetfindertest.service;

import com.spring.fleetfindertest.model.Corporation;
import com.spring.fleetfindertest.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorporationService {
    private final CorporationRepository corporationRepository;

    @Autowired
    public CorporationService(CorporationRepository corporationRepository) {
        this.corporationRepository = corporationRepository;
    }
    public Corporation saveCorporation(Corporation corporation){
        return corporationRepository.save(corporation);
    }
}
