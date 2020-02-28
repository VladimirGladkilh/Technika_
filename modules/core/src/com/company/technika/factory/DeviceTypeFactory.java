package com.company.technika.factory;

import com.company.technika.entity.DeviceType;

public interface DeviceTypeFactory {
    DeviceType create(String deviceName);
}
