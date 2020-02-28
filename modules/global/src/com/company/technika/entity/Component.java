package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s (%s)|device,serialNumber")
@Table(name = "TECHNIKA_COMPONENT")
@Entity(name = "technika_Component")
public class Component extends StandardEntity {
    private static final long serialVersionUID = -8318975988050538217L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_ID")
    protected Device device;

    @NotNull
    @Column(name = "SERIAL_NUMBER", nullable = false)
    protected String serialNumber;

    @Column(name = "PRICE")
    protected Double price;

    @Column(name = "LOW_PRICE")
    protected Double lowPrice;

    @Column(name = "BUSY")
    protected Boolean busy;

    @OnDelete(DeletePolicy.UNLINK)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COSTNAME_ID")
    protected Cost costName;

    public void setCostName(Cost costName) {
        this.costName = costName;
    }

    public Cost getCostName() {
        return costName;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}