package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "TECHNIKA_POST")
@Entity(name = "technika_Post")
@NamePattern("%s|name")
public class Post extends StandardEntity {
    private static final long serialVersionUID = -5542538873546700458L;

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