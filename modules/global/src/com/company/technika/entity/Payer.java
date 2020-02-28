package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "TECHNIKA_PAYER")
@Entity(name = "technika_Payer")
public class Payer extends StandardEntity {
    private static final long serialVersionUID = -7061723468653851079L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Column(name = "PRIM")
    protected String prim;

    public String getPrim() {
        return prim;
    }

    public void setPrim(String prim) {
        this.prim = prim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}