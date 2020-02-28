package com.company.technika.factory;

import com.company.technika.entity.DeviceType;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceTypeFactoryImpl implements DeviceTypeFactory {
    private  final Metadata metadata;

    @Autowired
    private DeviceTypeFactoryImpl(Metadata metadata){
        this.metadata = metadata;
    }
    @Override
    public DeviceType create(String deviceName) {
        DeviceType deviceType= metadata.create(DeviceType.class);
        deviceType.setName(deviceName);
        return deviceType;
    }
}
