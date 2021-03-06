package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "TECHNIKA_VENDOR")
@Entity(name = "technika_Vendor")
public class Vendor extends StandardEntity {
    private static final long serialVersionUID = 8991966325538225836L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}