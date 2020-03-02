package com.company.technika.factory;

import com.company.technika.entity.Component;
import com.company.technika.entity.Cost;
import com.company.technika.entity.Device;

public interface ComponentFactory {
    Component create(Device device, String serialNumber, Double price, Double lowPrice, Cost cost, Boolean busy);
    Component create(Device device, String serialNumber, Double price, Double lowPrice, Cost cost);
    Component create(Device device, String serialNumber, Double price, Double lowPrice);
    Component create(Device device, String serialNumber, Double price);
    Component create(Device device, String serialNumber);
    Component create(String deviceName, String serialNumber, Double price, Double lowPrice, String costName, Boolean busy);
    Component create(String deviceName, String serialNumber, Double price, Double lowPrice, String costName);
    Component create(String deviceName, String serialNumber, Double price, Double lowPrice);
    Component create(String deviceName, String serialNumber, Double price);
    Component create(String deviceName, String serialNumber);
    Component create(String deviceName, String serialNumber, String price, String lowPrice, String costName, String prim);
}
