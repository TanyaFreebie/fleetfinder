package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CorpRepository extends JpaRepository<Corporation, Long> {

    @Query(value = "SELECT * FROM characters WHERE is_active = ?1", nativeQuery = true)
    List<Corporation> findActive(Boolean isActive);
}
