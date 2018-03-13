package com.plankdev.jwtsecurity.controller;

import com.plankdev.jwtsecurity.dataaccess.Application;
import com.plankdev.jwtsecurity.dataaccess.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping( value = "/api/users/{userId}/applications", produces = MediaType.APPLICATION_JSON_VALUE )
public class ApplicationRestController {
    private ApplicationService applicationService;

    @Autowired
    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createApplication(@PathVariable Long userId, @RequestBody Application application) {
        Optional<Application> createdApplicationOpt = applicationService.createApplication(application);
        ResponseEntity<?> response = buildUserResponseEntity(createdApplicationOpt);
        return response;
    }

    private ResponseEntity<?> buildUserResponseEntity(Optional<Application> createApplicationOpt) {
        ResponseEntity<?> response;

        if(createApplicationOpt.isPresent()) {
            Application createApplication = createApplicationOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(createApplication.getId()).toUri();
            response = ResponseEntity.created(location).body(createApplication);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }

}
