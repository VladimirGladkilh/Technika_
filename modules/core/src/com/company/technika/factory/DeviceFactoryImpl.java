package com.company.technika.factory;

import com.company.technika.entity.Device;
import com.company.technika.entity.DeviceType;
import com.company.technika.entity.Vendor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DeviceFactoryImpl implements DeviceFactory {
    private final Metadata metadata;

    @Autowired
    private DeviceFactoryImpl(Metadata metadata){
        this.metadata = metadata;
    }

    @Override
    public Device create(DeviceType deviceType, Vendor vendor, String model, String prim) {
        Device device=metadata.create(Device.class);
        device.setType(deviceType);
        device.setVendor(vendor);
        device.setModel(model);
        device.setPrim(prim);
        return device;
    }

    @Override
    public Device create(DeviceType deviceType, Vendor vendor, String model) {
        Device device=metadata.create(Device.class);
        device.setType(deviceType);
        device.setVendor(vendor);
        device.setModel(model);
        return device;
    }

    @Override
    public Device create(String deviceName, String vendorName, String model, String prim) {
        Device device=metadata.create(Device.class);
        if (!deviceName.equals("")){
            DeviceType deviceType;
            deviceType= getDeviceTypeByName(deviceName);
            if (deviceType != null){
                device.setType(deviceType);
            }
        }
        if (!vendorName.equals("")){
            Vendor vendor;
            vendor = getVendorByName(vendorName);
            if (vendor != null){
                device.setVendor(vendor);
            }
        }

        device.setModel(model);
        device.setPrim(prim);
        return device;
    }

    @Override
    public Device create(String deviceName, String vendorName, String model) {
        Device device=metadata.create(Device.class);
        if (!deviceName.equals("")){
            DeviceType deviceType;
            deviceType= getDeviceTypeByName(deviceName);
            if (deviceType != null){
                device.setType(deviceType);
            }
        }
        if (!vendorName.equals("")){
            Vendor vendor;
            vendor = getVendorByName(vendorName);
            if (vendor != null){
                device.setVendor(vendor);
            }
        }
        device.setModel(model);
        return device;
    }

    @Inject
    private DataManager dataManager;

    private DeviceType getDeviceTypeByName(String deviceName){
        String deviceTypeQuery="select p from technika_DeviceType p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<DeviceType> lcp = LoadContext.create(DeviceType.class)
                .setQuery(LoadContext.createQuery(deviceTypeQuery)
                        .setParameter("name", deviceName.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }

    private Vendor getVendorByName(String vendorName){
        String vendorQuery="select p from technika_Vendor p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Vendor> lcp = LoadContext.create(Vendor.class)
                .setQuery(LoadContext.createQuery(vendorQuery)
                        .setParameter("name", vendorName.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }
}
