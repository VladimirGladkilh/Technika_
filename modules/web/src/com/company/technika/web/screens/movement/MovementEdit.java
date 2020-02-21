package com.company.technika.web.screens.movement;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Movement;

@UiController("technika_Movement.edit")
@UiDescriptor("movement-edit.xml")
@EditedEntityContainer("movementDc")
@LoadDataBeforeShow
public class MovementEdit extends StandardEditor<Movement> {
}