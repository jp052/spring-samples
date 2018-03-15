package com.plankdev.jwtsecurity.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    public Optional<Vehicle> findByName(String name);
}
