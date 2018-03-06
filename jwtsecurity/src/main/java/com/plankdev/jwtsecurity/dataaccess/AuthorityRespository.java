package com.plankdev.jwtsecurity.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRespository extends JpaRepository<Authority, Long> {
    public static final String ROLE_USER_DB_NAME = "ROLE_USER";
    public static final String ROLE_USER_APP_NAME = "USER";

    public Optional<Authority> findByName(String name);

}
