package com.plankdev.jwtsecurity.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;

@RequestMapping("/users")
@RestController
public class UserRestController {

    UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userRepository.save(new User(user.getName(), user.getPassword(), 1));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();

        ResponseEntity response = ResponseEntity.created(location).build();

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        throw new NotImplementedException();
    }

    @PutMapping("/{userId}/upgrade")
    public ResponseEntity<?> upgradeUser(@RequestBody User user) {
        //get user by id
        //trigger payment
        //if successful contiue otherwise abort
        //change userType to 2

        throw new NotImplementedException();
    }
}
