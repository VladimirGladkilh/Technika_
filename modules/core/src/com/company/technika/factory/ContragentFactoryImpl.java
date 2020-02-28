package com.company.technika.factory;

import com.company.technika.entity.Contragent;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContragentFactoryImpl implements ContragentFactory {
    private final Metadata metadata;
    @Autowired
    private ContragentFactoryImpl(Metadata metadata){
        this.metadata = metadata;
    }
    @Override
    public Contragent create(String contragentName, String contact, String phone, String prim) {
        Contragent contragent = metadata.create(Contragent.class);
        contragent.setName(contragentName);
        contragent.setContact(contact);
        contragent.setPhone(phone);
        contragent.setPrim(prim);
        return contragent;
    }
    @Override
    public Contragent create(String contragentName, String contact, String phone) {
        Contragent contragent = metadata.create(Contragent.class);
        contragent.setName(contragentName);
        contragent.setContact(contact);
        contragent.setPhone(phone);
        return contragent;
    }

    @Override
    public Contragent create(String contragentName, String contact) {
        Contragent contragent = metadata.create(Contragent.class);
        contragent.setName(contragentName);
        contragent.setContact(contact);
        return contragent;
    }

    @Override
    public Contragent create(String contragentName) {
        Contragent contragent = metadata.create(Contragent.class);
        contragent.setName(contragentName);
        return contragent;
    }
}
