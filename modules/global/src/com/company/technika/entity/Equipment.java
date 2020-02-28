package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|technika,component")
@Table(name = "TECHNIKA_EQUIPMENT")
@Entity(name = "technika_Equipment")
public class Equipment extends StandardEntity {
    private static final long serialVersionUID = 6023256051658221557L;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TECHNIKA_ID")
    protected Technika technika;

    @OnDelete(DeletePolicy.UNLINK)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPONENT_ID")
    protected Component component;

    @Column(name = "PRIM")
    protected String prim;

    public String getPrim() {
        return prim;
    }

    public void setPrim(String prim) {
        this.prim = prim;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Technika getTechnika() {
        return technika;
    }

    public void setTechnika(Technika technika) {
        this.technika = technika;
    }
}