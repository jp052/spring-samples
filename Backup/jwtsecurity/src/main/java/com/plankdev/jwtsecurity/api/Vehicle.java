package com.plankdev.jwtsecurity.api;

import com.plankdev.jwtsecurity.security.dataaccess.ApiApp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private ApiApp apiApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApiApp getApiApp() {
        return apiApp;
    }

    public void setApiApp(ApiApp apiApp) {
        this.apiApp = apiApp;
    }
}
