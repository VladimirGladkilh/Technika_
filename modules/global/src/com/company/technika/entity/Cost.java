package com.company.technika.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamePattern("№%s от %s %s|num,dat,subject")
@Table(name = "TECHNIKA_COST")
@Entity(name = "technika_Cost")
public class Cost extends StandardEntity {
    private static final long serialVersionUID = 8856393999773123214L;

    @NotNull
    @Column(name = "NUM", nullable = false)
    protected String num;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "DAT", nullable = false)
    protected Date dat;

    @OnDelete(DeletePolicy.UNLINK)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRAGENT_ID")
    protected Contragent contragent;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_DOC_ID")
    protected FileDescriptor mainDoc;

    @NotNull
    @Column(name = "SUBJECT", nullable = false, length = 512)
    protected String subject;

    @Column(name = "COST_PRICE")
    protected Double costPrice;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPD_ID")
    protected FileDescriptor upd;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYER_ID")
    protected Payer payer;

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public FileDescriptor getUpd() {
        return upd;
    }

    public void setUpd(FileDescriptor upd) {
        this.upd = upd;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public FileDescriptor getMainDoc() {
        return mainDoc;
    }

    public void setMainDoc(FileDescriptor mainDoc) {
        this.mainDoc = mainDoc;
    }

    public Contragent getContragent() {
        return contragent;
    }

    public void setContragent(Contragent contragent) {
        this.contragent = contragent;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}