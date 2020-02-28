package com.company.technika.factory;

import com.company.technika.entity.Office;

public interface OfficeFactory {
    Office create(String name, String city, String street, String house, String office);
    Office create(String name, String city, String street, String house);
    Office create(String name, String city, String street);
    Office create(String name, String city);
    Office create(String name);


}
