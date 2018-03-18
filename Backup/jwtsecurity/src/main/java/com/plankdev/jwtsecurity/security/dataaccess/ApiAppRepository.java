package com.plankdev.jwtsecurity.security.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiAppRepository extends JpaRepository<ApiApp, Long> {

    public Optional<ApiApp> findByName(String name);
}
