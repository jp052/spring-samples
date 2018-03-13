package com.plankdev.jwtsecurity.controller;

import com.plankdev.jwtsecurity.dataaccess.UserService;
import com.plankdev.jwtsecurity.dataaccess.User;
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
public class UserRestController {

    @Autowired
    private UserService userService;

    //# create user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> createUserOpt = userService.createUser(user);
        ResponseEntity<?> response = buildUserResponseEntity(createUserOpt);

        return response;
    }

    //# read user
    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User readUser(@PathVariable Long userId ) {
        return this.userService.findById( userId );
    }


    //# update user
    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
        if(user.getId() == null || user.getId() != userId) {
            throw new IllegalStateException("PathVariable and RequestBody id needs to be set and same value");
        }
        Optional<User> updatedUserOpt = userService.updateUser(user);
        ResponseEntity<?> response = buildUserResponseEntity(updatedUserOpt);

        return response;
    }

    //# delete user
    @DeleteMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    //# list users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers() {
        return this.userService.findAll();
    }

    //# whoami
    //how to implemten this properly using Resful routes? Separate resource calls StatisticsController?
    @GetMapping(value= "/whoami")
    @PreAuthorize("hasRole('USER')")
    public User whoami(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return user;
    }

    private ResponseEntity<?> buildUserResponseEntity(Optional<User> createUserOpt) {
        ResponseEntity<?> response;

        if(createUserOpt.isPresent()) {
            User createUser = createUserOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(createUser.getId()).toUri();
            response = ResponseEntity.created(location).body(createUser);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }
}
