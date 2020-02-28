package com.company.technika.factory;

import com.company.technika.entity.Office;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfficeFactoryImpl implements OfficeFactory {
    private final Metadata metadata;

    @Autowired
    private OfficeFactoryImpl(Metadata metadata){
        this.metadata = metadata;
    }


    @Override
    public Office create(String name, String city, String street, String house, String numOffice) {
        Office office = metadata.create(Office.class);
        office.setName(name);
        office.setCity(city);
        office.setStreet(street);
        office.setHouse(house);
        office.setOffice(numOffice);

        return office;
    }

    @Override
    public Office create(String name, String city) {
        Office office = metadata.create(Office.class);
        office.setName(name);
        office.setCity(city);

        return office;
    }

    @Override
    public Office create(String name) {
        Office office = metadata.create(Office.class);
        office.setName(name);

        return office;
    }

    @Override
    public Office create(String name, String city, String street, String house) {
        Office office = metadata.create(Office.class);
        office.setName(name);
        office.setCity(city);
        office.setStreet(street);
        office.setHouse(house);
        return office;
    }

    @Override
    public Office create(String name, String city, String street) {
        Office office = metadata.create(Office.class);
        office.setName(name);
        office.setCity(city);
        office.setStreet(street);
        return office;
    }
}
