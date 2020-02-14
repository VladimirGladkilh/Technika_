package com.company.technika.web.screens.vendor;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Vendor;

@UiController("technika_Vendor.edit")
@UiDescriptor("vendor-edit.xml")
@EditedEntityContainer("vendorDc")
@LoadDataBeforeShow
public class VendorEdit extends StandardEditor<Vendor> {
}