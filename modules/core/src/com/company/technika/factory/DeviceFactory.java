package com.company.technika.factory;

import com.company.technika.entity.Device;
import com.company.technika.entity.DeviceType;
import com.company.technika.entity.Vendor;

public interface DeviceFactory {
    Device create(DeviceType deviceType, Vendor vendor, String model, String prim);
    Device create(DeviceType deviceType, Vendor vendor, String model);
    Device create(String deviceName, String vendorName, String model, String prim);
    Device create(String deviceName, String vendorName, String model);
}
