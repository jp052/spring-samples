package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    //Maybe @Transactional is needed, as it is a custom method
    public Optional<AppUser> findByUsername(String username);

}
