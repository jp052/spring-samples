package com.plankdev.jwtsecurity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @JsonIgnore
    private String password;

    /*
    * TODO: Craete Enum of UserType
    * userType:
    * 1 - general user
    * 2 - CSR (Customer Service Representative)
    * 3 - admin
    */
    private Integer userType;

    //Needed for JPA
    public User() {
    }

    public User(String name, String password, Integer userType) {
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserType(Integer userType){
        this.userType = userType;
    }
    public Integer getUserType(){
        return this.userType;
    }
}
