package com.plankdev.jwtsecurity.security.dataaccess;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ApiKey {

    @Id
    @GeneratedValue
    private Long id;

    private String apiKeyToken;

    private boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiKeyToken() {
        return apiKeyToken;
    }

    public void setApiKeyToken(String apiKeyToken) {
        this.apiKeyToken = apiKeyToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
