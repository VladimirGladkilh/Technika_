package com.company.technika.web.screens.device;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Device;

@UiController("technika_Device.edit")
@UiDescriptor("device-edit.xml")
@EditedEntityContainer("deviceDc")
@LoadDataBeforeShow
public class DeviceEdit extends StandardEditor<Device> {
}