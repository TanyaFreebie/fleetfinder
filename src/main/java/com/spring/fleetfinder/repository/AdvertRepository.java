package com.spring.fleetfinder.repository;

import com.spring.fleetfinder.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advertisement, Long> {
}
