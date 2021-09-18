package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

}
