package com.plankdev.jwtsecurity.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Authority implements GrantedAuthority {

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
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

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

    public void addUser(User user) {
       /* users.add( user );
        GrantedAuthority thisAuthority = (GrantedAuthority) this;
        user.getAuthorities().add(thisAuthority);*/
    }

    public void removeUser(User use) {
        /*addresses.remove( address );
        address.getOwners().remove( this );*/
    }


    @Override
    public String getAuthority() {
        return this.name;
    }
}
