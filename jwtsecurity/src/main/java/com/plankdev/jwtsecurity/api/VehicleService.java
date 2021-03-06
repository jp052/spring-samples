package com.plankdev.jwtsecurity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plankdev.jwtsecurity.security.dataaccess.AppUser;
import com.plankdev.jwtsecurity.security.dataaccess.Application;

import java.util.Optional;

@Service
public class VehicleService {
    private VehicleRepository vehicleRepo;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public Optional<Vehicle> createVehicle(Vehicle vehicle, Application app) {
    	vehicle.setApplication(app);
        Optional<Vehicle> vehicleOptional = Optional.of(vehicleRepo.save(vehicle));
        return vehicleOptional;
    }

	public Optional<Vehicle>  findByIdAndApplicationId(Long vehicleId, Application app) {
		Optional<Vehicle> vehicle = vehicleRepo.findByIdAndApplicationName(vehicleId, app.getName());
		return vehicle;
	}
}
