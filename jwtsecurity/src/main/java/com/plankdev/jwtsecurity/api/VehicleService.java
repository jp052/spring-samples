package com.plankdev.jwtsecurity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {
    private VehicleRepository vehicleRepo;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public Optional<Vehicle> createVehicle(Vehicle vehicle) {
        Optional<Vehicle> vehicleOptional = Optional.of(vehicleRepo.save(vehicle));
        return vehicleOptional;
    }
}
