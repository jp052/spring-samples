package com.plankdev.jwtsecurity.security.controller;

import com.plankdev.jwtsecurity.security.dataaccess.ApiApp;
import com.plankdev.jwtsecurity.security.dataaccess.ApiAppService;
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
public class ApiAppRestController {
    private ApiAppService apiAppService;

    @Autowired
    public ApiAppRestController(ApiAppService apiAppService) {
        this.apiAppService = apiAppService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createApplication(@PathVariable Long userId, @RequestBody ApiApp apiApp) {
        Optional<ApiApp> createdApplicationOpt = apiAppService.createApplication(apiApp);
        ResponseEntity<?> response = buildApiAppResponseEntity(createdApplicationOpt);
        return response;
    }

    private ResponseEntity<?> buildApiAppResponseEntity(Optional<ApiApp> createApiAppOpt) {
        ResponseEntity<?> response;

        if(createApiAppOpt.isPresent()) {
            ApiApp createApiApp = createApiAppOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}") //FIXME: adds double id for update, needs fix.
                    .buildAndExpand(createApiApp.getId()).toUri();
            response = ResponseEntity.created(location).body(createApiApp);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }

}
