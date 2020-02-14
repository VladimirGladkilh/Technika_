package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "TECHNIKA_DEVICE_TYPE")
@Entity(name = "technika_DeviceType")
public class DeviceType extends StandardEntity {
    private static final long serialVersionUID = -1996458636061683430L;

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