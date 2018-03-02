package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username )
                .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    public Optional<User> createUser(User user) {
        Optional<User> createdUser = Optional.ofNullable(userRepository.save(user));
        return createdUser;
    }
}
