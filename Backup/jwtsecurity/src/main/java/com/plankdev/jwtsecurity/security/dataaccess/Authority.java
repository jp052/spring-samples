package com.plankdev.jwtsecurity.security.dataaccess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Authority implements GrantedAuthority {


    private static final Log LOGGER = LogFactory.getLog(Authority.class);

    @Id
    @GeneratedValue
    private Long id;

    //TODO: Implement Enum AuthorityName, see: https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/model/security/AuthorityName.java
    @NotNull
    private String name;

    public Authority() {
    }

    public Authority(String name) {
        this.name = name;
    }

    //inverse/child side of relation
    @ManyToMany(mappedBy = "authorities")
    private List<ApiUser> apiUsers = new ArrayList<>();

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

    public void addUser(ApiUser apiUser) {
        if (!apiUsers.contains(apiUser)) {
            apiUsers.add(apiUser);
        } else {
            LOGGER.info("user: " + apiUser.getUsername() + "already exists in authority: " + this.name);
        }

        Collection<? extends GrantedAuthority> existingAuthorities = apiUser.getAuthorities();
        if (!existingAuthorities.contains(this)) {
            List<Authority> authoritiesToAdd = new ArrayList<>();

            //cast existing GrantedAuthorities to Authority
            for (GrantedAuthority authority : existingAuthorities) {
                if (authority instanceof Authority) {
                    authoritiesToAdd.add((Authority) authority);
                }
            }

            authoritiesToAdd.add(this);
            apiUser.setAuthorities(authoritiesToAdd);
        } else {
            LOGGER.info("authority: " + this.name + "already exists in user: " + apiUser.getUsername());
        }

    }

    public void removeUser(ApiUser use) {
        /*FIXME:
        addresses.remove( address );
        address.getOwners().remove( this );*/
    }


    @Override
    public String getAuthority() {
        return this.name;
    }
}
