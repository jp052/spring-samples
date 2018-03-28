package com.plankdev.jwtsecurity.security.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    public Optional<ApiKey> findByJwtToken(String jwtToken);
}
