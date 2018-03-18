package com.plankdev.jwtsecurity.security.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    //Maybe @Transactional is needed, as it is a custom method
    public Optional<ApiUser> findByUsername(String username);

}
