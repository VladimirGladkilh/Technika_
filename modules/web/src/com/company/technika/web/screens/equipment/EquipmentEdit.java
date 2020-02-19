package com.company.technika.web.screens.equipment;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Equipment;

@UiController("technika_Equipment.edit")
@UiDescriptor("equipment-edit.xml")
@EditedEntityContainer("equipmentDc")
@LoadDataBeforeShow
public class EquipmentEdit extends StandardEditor<Equipment> {
}