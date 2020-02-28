package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|familia,imya")
@Table(name = "TECHNIKA_PERSON")
@Entity(name = "technika_Person")
public class Person extends StandardEntity {
    private static final long serialVersionUID = 6176851284256698885L;

    @NotNull
    @Column(name = "FAMILIA", nullable = false)
    protected String familia;

    @NotNull
    @Column(name = "IMYA", nullable = false)
    protected String imya;

    @Column(name = "OTCHESTVO")
    protected String otchestvo;

    @Column(name = "EMAIL", unique = true, length = 50)
    protected String email;

    @Column(name = "PHONE", length = 50)
    protected String phone;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFICE_ID")
    protected Office office;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    protected Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getImya() {
        return imya;
    }

    public void setImya(String imya) {
        this.imya = imya;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}