package com.spring.fleetfindertest.repository;

import com.spring.fleetfindertest.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advertisement, Long> {
}
