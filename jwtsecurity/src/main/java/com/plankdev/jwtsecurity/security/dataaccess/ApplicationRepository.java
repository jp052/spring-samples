package com.plankdev.jwtsecurity.security.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    public Optional<Application> findByName(String name);
}
