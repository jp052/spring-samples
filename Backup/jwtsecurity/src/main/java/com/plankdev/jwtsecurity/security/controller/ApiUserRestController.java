package com.plankdev.jwtsecurity.security.controller;

import com.plankdev.jwtsecurity.security.dataaccess.ApiUser;
import com.plankdev.jwtsecurity.security.dataaccess.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE )
public class ApiUserRestController {

    @Autowired
    private ApiUserService apiUserService;

    //# create apiUser
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody ApiUser apiUser) {
        Optional<ApiUser> createUserOpt = apiUserService.createUser(apiUser);
        ResponseEntity<?> response = buildUserResponseEntity(createUserOpt);

        return response;
    }

    //# read user
    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiUser readUser(@PathVariable Long userId ) {
        return this.apiUserService.findById( userId );
    }


    //# update apiUser
    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody ApiUser apiUser) {
        if(apiUser.getId() == null || apiUser.getId() != userId) {
            throw new IllegalStateException("PathVariable and RequestBody id needs to be set and same value");
        }
        Optional<ApiUser> updatedUserOpt = apiUserService.updateUser(apiUser);
        ResponseEntity<?> response = buildUserResponseEntity(updatedUserOpt);

        return response;
    }

    //# delete user
    @DeleteMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        apiUserService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    //# list users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ApiUser> listUsers() {
        return this.apiUserService.findAll();
    }

    //# whoami
    //how to implemten this properly using Resful routes? Separate resource calls StatisticsController?
    @GetMapping(value= "/whoami")
    @PreAuthorize("hasRole('USER')")
    public ApiUser whoami(Principal principal) {
        ApiUser apiUser = apiUserService.findByUsername(principal.getName());
        return apiUser;
    }

    private ResponseEntity<?> buildUserResponseEntity(Optional<ApiUser> createUserOpt) {
        ResponseEntity<?> response;

        if(createUserOpt.isPresent()) {
            ApiUser createApiUser = createUserOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(createApiUser.getId()).toUri();
            response = ResponseEntity.created(location).body(createApiUser);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }
}
