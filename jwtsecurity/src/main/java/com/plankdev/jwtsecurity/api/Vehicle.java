package com.plankdev.jwtsecurity.api;

import com.plankdev.jwtsecurity.dataaccess.Application;

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
    private Application application;
}
