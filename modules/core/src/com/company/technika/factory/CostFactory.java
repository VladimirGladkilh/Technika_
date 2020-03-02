package com.company.technika.factory;

import com.company.technika.entity.Contragent;
import com.company.technika.entity.Cost;
import com.company.technika.entity.Payer;
import com.haulmont.cuba.core.entity.FileDescriptor;


import java.util.Date;

public interface CostFactory {
    Cost create(String num, Date dat, Contragent contragent, FileDescriptor mainDoc, String subject, Double costPrice, FileDescriptor updDoc, Payer payer);
    Cost create(String num, Date dat, Contragent contragent, FileDescriptor mainDoc, String subject, Double costPrice, Payer payer);
    Cost create(String num, Date dat, Contragent contragent, String subject, Double costPrice, FileDescriptor updDoc, Payer payer);
    Cost create(String num, Date dat, Contragent contragent, String subject, Double costPrice, Payer payer);
    Cost create(String num, Date dat, String contragentName, FileDescriptor mainDoc, String subject, Double costPrice, FileDescriptor updDoc, String payerName);
    Cost create(String num, Date dat, String contragentName, FileDescriptor mainDoc, String subject, Double costPrice, String payerName);
    Cost create(String num, Date dat, String contragentName, String subject, Double costPrice, FileDescriptor updDoc, String payerName);
    Cost create(String num, Date dat, String contragentName, String subject, Double costPrice, String payerName);
    Cost create(String num, String dat, String contragentName, String subject, String costPrice, String payerName);
}
