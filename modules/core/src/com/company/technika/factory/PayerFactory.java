package com.company.technika.factory;

import com.company.technika.entity.Payer;

public interface PayerFactory {
    Payer create(String payerName);
}
