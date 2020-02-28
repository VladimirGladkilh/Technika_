package com.company.technika.factory;

import com.company.technika.entity.Vendor;

public interface VendorFactory {
    Vendor create(String vendorName);
}
