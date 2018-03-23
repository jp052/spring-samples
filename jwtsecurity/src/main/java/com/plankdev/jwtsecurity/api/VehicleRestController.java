package com.plankdev.jwtsecurity.api;

import com.plankdev.jwtsecurity.restcommons.ResponseBuilder;
import com.plankdev.jwtsecurity.security.dataaccess.AppUser;
import com.plankdev.jwtsecurity.security.dataaccess.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RequestMapping(value = "/api/v1/vehicles")
@RestController
public class VehicleRestController {

    private VehicleService vehicleService;
    private UserService userService;

    @Autowired
    public VehicleRestController(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(Principal principal, @RequestBody Vehicle vehicle) {
    	AppUser appUser = userService.findByUsername(principal.getName());
        Optional<Vehicle> createdVehicle = vehicleService.createVehicle(vehicle, appUser);
        ResponseEntity<?> response = ResponseBuilder.buildNewModelResponseEntity(createdVehicle);

        return response;
    }

}
