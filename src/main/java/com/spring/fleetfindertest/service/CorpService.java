package com.spring.fleetfindertest.service;

import com.spring.fleetfindertest.model.Corporation;
import com.spring.fleetfindertest.model.Pilot;
import com.spring.fleetfindertest.repository.CorpRepository;
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

    //for corps_list we need more like this, not just all, but this doesn't works
//    public List<Corporation> findByIsActive(Boolean isActive){ return corpRepository.findActive(isActive);}
}
