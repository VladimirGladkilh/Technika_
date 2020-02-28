package com.company.technika.factory;

import com.company.technika.entity.Payer;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayerFactoryImpl implements PayerFactory {
    private final Metadata metadata;

    @Autowired
    private PayerFactoryImpl(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public Payer create(String payerName) {
        Payer payer= metadata.create(Payer.class);
        payer.setName(payerName);
        return payer;
    }
}
