package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "TECHNIKA_OFFICE")
@Entity(name = "technika_Office")
public class Office extends StandardEntity {
    private static final long serialVersionUID = 7942490301340630776L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Column(name = "CITY")
    protected String city;

    @Column(name = "STREET")
    protected String street;

    @Column(name = "HOUSE", length = 20)
    protected String house;

    @Column(name = "OFFICE", length = 20)
    protected String office;

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    public void updateOfficeName() {
        if (street.equals("")){
            name = city;
        } else {
            name = createOfficeName(city, street, house);
        }
    }


    private static String createOfficeName(String city, String street, String house){
        return  String.format("%s. %s %s", city, street, house).trim();
    }
}