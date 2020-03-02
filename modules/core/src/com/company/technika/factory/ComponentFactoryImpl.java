package com.company.technika.factory;

import com.company.technika.entity.Component;
import com.company.technika.entity.Cost;
import com.company.technika.entity.Device;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

@org.springframework.stereotype.Component
public class ComponentFactoryImpl implements ComponentFactory {
    private final Metadata metadata;
    @Autowired
    private ComponentFactoryImpl(Metadata metadata){
        this.metadata = metadata;
    }
    @Override
    public Component create(Device device, String serialNumber, Double price, Double lowPrice, Cost cost, Boolean busy) {
        Component component= metadata.create(Component.class);
        component.setDevice(device);
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        component.setCostName(cost);
        component.setBusy(busy);
        return component;
    }

    @Override
    public Component create(Device device, String serialNumber, Double price, Double lowPrice, Cost cost) {
        Component component= metadata.create(Component.class);
        component.setDevice(device);
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        component.setCostName(cost);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(Device device, String serialNumber, Double price, Double lowPrice) {
        Component component= metadata.create(Component.class);
        component.setDevice(device);
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(Device device, String serialNumber, Double price) {
        Component component= metadata.create(Component.class);
        component.setDevice(device);
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(Device device, String serialNumber) {
        Component component= metadata.create(Component.class);
        component.setDevice(device);
        component.setSerialNumber(serialNumber);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber, Double price, Double lowPrice, String costName, Boolean busy) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        if (!costName.equals("")){
            Cost cost=getCostByNumber(costName);
            if (cost != null){
                component.setCostName(cost);
            }
        }
        component.setBusy(busy);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber, Double price, Double lowPrice, String costName) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        if (!costName.equals("")){
            Cost cost=getCostByNumber(costName);
            if (cost != null){
                component.setCostName(cost);
            }
        }
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber, Double price, Double lowPrice) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setLowPrice(lowPrice);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber, Double price) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);
        component.setPrice(price);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);
        component.setBusy(false);
        return component;
    }

    @Override
    public Component create(String deviceName, String serialNumber, String price, String lowPrice, String costName, String prim) {
        Component component= metadata.create(Component.class);
        if (!deviceName.equals("")){
            Device device = getDeviceByName(deviceName);
            if (device != null){
                component.setDevice(device);
            }
        }
        component.setSerialNumber(serialNumber);

        if (!costName.equals("")){
            Cost cost=getCostByNumber(costName);
            if (cost != null){
                component.setCostName(cost);
            }
        }

        if (!price.equals("")) {
            try {
                Double priceValue = Double.valueOf(price);
                component.setPrice(priceValue);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if (!lowPrice.equals("")){
            try {
                Double lowPriceValue = Double.valueOf(lowPrice);
                component.setLowPrice(lowPriceValue);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        component.setPrim(prim);
        component.setBusy(false);
        return component;
    }

    @Inject
    private DataManager dataManager;

    private Cost getCostByNumber(String costNumber){
        String costQuery="select p from technika_Cost p where lower(trim(p.num))=:name and p.deleteTs is null";
        LoadContext<Cost> lcp = LoadContext.create(Cost.class)
                .setQuery(LoadContext.createQuery(costQuery)
                        .setParameter("name", costNumber.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }

    private Device getDeviceByName(String deviceName){
        String deviceQuery="select p from technika_Device p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Device> lcp = LoadContext.create(Device.class)
                .setQuery(LoadContext.createQuery(deviceQuery)
                        .setParameter("name", deviceName.toLowerCase())
                        .setMaxResults(1));
        return dataManager.load(lcp);
    }
}
