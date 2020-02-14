package com.company.technika.web.screens.devicetype;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.DeviceType;

import javax.inject.Inject;

@UiController("technika_DeviceType.browse")
@UiDescriptor("device-type-browse.xml")
@LookupComponent("deviceTypesTable")
@LoadDataBeforeShow
public class DeviceTypeBrowse extends StandardLookup<DeviceType> {
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionContainer<DeviceType> deviceTypesDc;
    @Inject
    private DataGrid<DeviceType> deviceTypesTable;

    @Subscribe("deviceTypesTable.create")
    public void onDeviceTypesTableCreate(Action.ActionPerformedEvent event) {
        DeviceType deviceType = dataContext.create(DeviceType.class);
        deviceTypesDc.getMutableItems().add(deviceType);
        deviceTypesTable.edit(deviceType);
    }

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();

    }
}