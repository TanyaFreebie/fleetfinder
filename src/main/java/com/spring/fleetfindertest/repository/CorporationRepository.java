package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporationRepository extends JpaRepository<Corporation,Long> {
}
