package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Corporation;
import com.spring.fleetfindertest.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorpRepository extends JpaRepository<Corporation, Long> {

}
