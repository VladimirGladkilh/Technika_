package com.company.technika.factory;

import com.company.technika.entity.Vendor;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendorFactoryImpl implements VendorFactory {
    private final Metadata metadata;

    @Autowired
    private VendorFactoryImpl(Metadata metadata) {
        this.metadata = metadata;
    }
    @Override
    public Vendor create(String vendorName) {
        Vendor vendor = metadata.create(Vendor.class);
        vendor.setName(vendorName);
        return vendor;
    }
}
