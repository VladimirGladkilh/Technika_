package com.company.technika.web.screens.devicetype;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.DeviceType;

@UiController("technika_DeviceType.edit")
@UiDescriptor("device-type-edit.xml")
@EditedEntityContainer("deviceTypeDc")
@LoadDataBeforeShow
public class DeviceTypeEdit extends StandardEditor<DeviceType> {
}