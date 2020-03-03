package com.company.technika.web.screens.technika;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Technika;

@UiController("technika_Technika.browse")
@UiDescriptor("technika-browse.xml")
@LookupComponent("technikasTable")
@LoadDataBeforeShow
public class TechnikaBrowse extends StandardLookup<Technika> {
}