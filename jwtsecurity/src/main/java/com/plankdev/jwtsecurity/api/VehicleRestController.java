package com.plankdev.jwtsecurity.api;

import com.plankdev.jwtsecurity.restcommons.Model;
import com.plankdev.jwtsecurity.restcommons.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RequestMapping(value = "/api/v1/vehicles")
@RestController
public class VehicleRestController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(Principal principal, @RequestBody Vehicle vehicle) {
        Optional<Vehicle> createdVehicle = vehicleService.createVehicle(vehicle);
        ResponseEntity<?> response = ResponseBuilder.buildNewResponseEntity(createdVehicle);

        return response;
    }

}
