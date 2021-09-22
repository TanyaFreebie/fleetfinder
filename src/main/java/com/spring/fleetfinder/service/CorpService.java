package com.spring.fleetfinder.service;

import com.spring.fleetfinder.model.Corporation;
import com.spring.fleetfinder.repository.CorpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service


public class CorpService {
    private final CorpRepository corpRepository;

    @Autowired
    public CorpService(CorpRepository corpRepository) {
        this.corpRepository = corpRepository;
    }

    public Corporation findById(Long id){
        return corpRepository.findById(id).orElse(null);
    }
    public List<Corporation> findAll(){
        return corpRepository.findAll();
    }
    public Corporation saveCorp( Corporation corporation){
        return corpRepository.save(corporation);
    }
    public List<Corporation> findActive(Boolean isActive){ return corpRepository.findActive(isActive);}
}
