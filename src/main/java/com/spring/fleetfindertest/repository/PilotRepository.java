package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    @Query(value = "SELECT * FROM characters WHERE is_active = ?1", nativeQuery = true)
    List<Pilot> findActive(Boolean isActive);
}
