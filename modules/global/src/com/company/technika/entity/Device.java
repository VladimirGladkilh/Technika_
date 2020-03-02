package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s %s|type,vendor,model")
@Table(name = "TECHNIKA_DEVICE")
@Entity(name = "technika_Device")
public class Device extends StandardEntity {
    private static final long serialVersionUID = -1611915218397165572L;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected DeviceType type;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VENDOR_ID")
    protected Vendor vendor;

    @NotNull
    @Column(name = "MODEL", nullable = false)
    protected String model;

    @Column(name = "PRIM", length = 512)
    protected String prim;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrim() {
        return prim;
    }

    public void setPrim(String prim) {
        this.prim = prim;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @PrePersist
    @PreUpdate
    public void updateName() {
        StringBuilder actualName= new StringBuilder();
        if (type != null){
            actualName.append(type.getName());
            actualName.append(" ");
        }
        if (vendor != null){
            actualName.append(vendor.getName());
            actualName.append(" ");
        }
        actualName.append(model);


        name = actualName.toString().trim();
    }

}