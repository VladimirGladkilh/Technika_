package com.company.technika.factory;

import com.company.technika.entity.Contragent;

public interface ContragentFactory {
    Contragent create(String contragentName, String contact, String phone, String prim);

    Contragent create(String contragentName, String contact, String phone);

    Contragent create(String contragentName, String contact);

    Contragent create(String contragentName);
}
