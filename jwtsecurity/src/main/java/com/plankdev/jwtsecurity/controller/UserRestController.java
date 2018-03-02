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

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping( value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserRestController {

    @Autowired
    private UserService userService;

    /*
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
		this.validateUser(userId);

		return this.accountRepository
				.findByUsername(userId)
				.map(account -> {
					Bookmark result = bookmarkRepository.save(new Bookmark(account,
							input.getUri(), input.getDescription()));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
				})
				.orElse(ResponseEntity.noContent().build());

	}
     */

    //# createUser
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> createUserOpt = userService.createUser(user);
        ResponseEntity<?> response;

        if(createUserOpt.isPresent()) {
            User createUser = createUserOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("userId")
                    .buildAndExpand(createUser.getId()).toUri();
            response = ResponseEntity.created(location).build();
        } else {
            response = ResponseEntity.noContent().build();
        }

        return response;
    }

    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User readUser(@PathVariable Long userId ) {
        return this.userService.findById( userId );
    }


    //updateUser

    //deleteUser


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers() {
        return this.userService.findAll();
    }

    //how to implemten this properly using Resful routes? Separate resource calls StatisticsController?
    @GetMapping(value= "/whoami")
    @PreAuthorize("hasRole('USER')")
    public User whoami(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return user;
    }
}
