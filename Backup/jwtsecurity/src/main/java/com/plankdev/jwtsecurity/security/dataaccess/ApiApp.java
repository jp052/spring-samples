package com.plankdev.jwtsecurity.security.dataaccess;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ApiApp {

    @Id
    @GeneratedValue
    private Long id;

    //name and apiUser must be unique key, as on user is only allowed to have one application with the same name.
    private String name;

    @ManyToOne
    private ApiKey apiKey;

   /* @ManyToOne
    private List<ApiKey> apiKeyBlacklist;*/

    //Parent side of relation
    @ManyToOne
    @JsonBackReference //prevents infinity loop
    private ApiUser apiUser;

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

    public ApiKey getApiKey() {
        return apiKey;
    }

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public ApiUser getApiUser() {
        return apiUser;
    }

    public void setApiUser(ApiUser apiUser) {
        this.apiUser = apiUser;
    }
}
