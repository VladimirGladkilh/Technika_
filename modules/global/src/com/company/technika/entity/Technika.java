package com.company.technika.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "TECHNIKA_TECHIKA")
@Entity(name = "technika_Techika")
public class Technika extends StandardEntity {
    private static final long serialVersionUID = -1209842386933506234L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "PRIM")
    protected String prim;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "technika")
    protected List<Equipment> equipment;

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

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