package com.company.technika.factory;

import com.company.technika.entity.Contragent;
import com.company.technika.entity.Cost;
import com.company.technika.entity.Payer;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.poi.ss.formula.functions.Today;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
@Component
public class CostFactoryImpl implements CostFactory {
    private final Metadata metadata;
    @Autowired
    private CostFactoryImpl (Metadata metadata){
        this.metadata = metadata;
    }
    @Override
    public Cost create(String num, Date dat, Contragent contragent, FileDescriptor mainDoc, String subject, Double costPrice, FileDescriptor updDoc, Payer payer) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        cost.setContragent(contragent);
        cost.setMainDoc(mainDoc);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setUpd(updDoc);
        cost.setPayer(payer);
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, Contragent contragent, FileDescriptor mainDoc, String subject, Double costPrice, Payer payer) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        cost.setContragent(contragent);
        cost.setMainDoc(mainDoc);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setPayer(payer);
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, Contragent contragent, String subject, Double costPrice, FileDescriptor updDoc, Payer payer) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        cost.setContragent(contragent);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setUpd(updDoc);
        cost.setPayer(payer);
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, Contragent contragent, String subject, Double costPrice, Payer payer) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        cost.setContragent(contragent);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setPayer(payer);
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, String contragentName, FileDescriptor mainDoc, String subject, Double costPrice, FileDescriptor updDoc, String payerName) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        if (!contragentName.equals("")){
            Contragent contragent= getContragentByName(contragentName);
            if (contragent != null){
                cost.setContragent(contragent);
            }

        }
        cost.setMainDoc(mainDoc);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setUpd(updDoc);
        if (!payerName.equals("")){
            Payer payer= getPayerByName(payerName);
            if (payer != null){
                cost.setPayer(payer);
            }

        }
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, String contragentName, FileDescriptor mainDoc, String subject, Double costPrice, String payerName) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        if (!contragentName.equals("")){
            Contragent contragent= getContragentByName(contragentName);
            if (contragent != null){
                cost.setContragent(contragent);
            }

        }
        cost.setMainDoc(mainDoc);
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        if (!payerName.equals("")){
            Payer payer= getPayerByName(payerName);
            if (payer != null){
                cost.setPayer(payer);
            }

        }
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, String contragentName, String subject, Double costPrice, FileDescriptor updDoc, String payerName) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        if (!contragentName.equals("")){
            Contragent contragent= getContragentByName(contragentName);
            if (contragent != null){
                cost.setContragent(contragent);
            }

        }
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        cost.setUpd(updDoc);
        if (!payerName.equals("")){
            Payer payer= getPayerByName(payerName);
            if (payer != null){
                cost.setPayer(payer);
            }

        }
        return cost;
    }

    @Override
    public Cost create(String num, Date dat, String contragentName, String subject, Double costPrice, String payerName) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        cost.setDat(dat);
        if (!contragentName.equals("")){
            Contragent contragent= getContragentByName(contragentName);
            if (contragent != null){
                cost.setContragent(contragent);
            }

        }
        cost.setSubject(subject);
        cost.setCostPrice(costPrice);
        if (!payerName.equals("")){
            Payer payer= getPayerByName(payerName);
            if (payer != null){
                cost.setPayer(payer);
            }

        }
        return cost;
    }

    @Override
    public Cost create(String num, String dat, String contragentName, String subject, String costPrice, String payerName) {
        Cost cost = metadata.create(Cost.class);
        cost.setNum(num);
        if (!dat.equals("")){
            try{
                Date date=new SimpleDateFormat("dd.MM.yyyy").parse(dat);
                cost.setDat(date);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            cost.setDat(new Date(System.currentTimeMillis()));
        }

        if (!contragentName.equals("")){
            Contragent contragent= getContragentByName(contragentName);
            if (contragent != null){
                cost.setContragent(contragent);
            }

        }
        cost.setSubject(subject);
        if (!costPrice.equals("")) {
            try {
                Double price = Double.parseDouble(costPrice);
                cost.setCostPrice(price);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (!payerName.equals("")){
            Payer payer= getPayerByName(payerName);
            if (payer != null){
                cost.setPayer(payer);
            }

        }
        return cost;
    }

    @Inject
    private DataManager dataManager;

    private Contragent getContragentByName(String contragentName){
        String contragentQuery="select p from technika_Contragent p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Contragent> lcp = LoadContext.create(Contragent.class)
                .setQuery(LoadContext.createQuery(contragentQuery)
                        .setParameter("name", contragentName.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }

    private Payer getPayerByName(String payerName){
        String payerQuery="select p from technika_Payer p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Payer> lcp = LoadContext.create(Payer.class)
                .setQuery(LoadContext.createQuery(payerQuery)
                        .setParameter("name", payerName.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }
}
