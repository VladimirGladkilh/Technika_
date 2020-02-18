package com.company.technika.web.screens.devicetype;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.DeviceType;
import com.haulmont.cuba.core.global.PersistenceHelper;

import javax.inject.Inject;
import java.util.UUID;

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

    @Inject
    private Notifications notifications;
    @Inject
    private Metadata metadata;
    DeviceType entityString = null;

    public void ShowMessage(String message) {
        notifications.create().withCaption(message).show();
    }

    public void CreateRecord(){
        if (!deviceTypesTable.isEditorActive())
        {
            entityString = dataContext.create(DeviceType.class);
            deviceTypesDc.getMutableItems().add(entityString); //добавляет пустую сущность
            deviceTypesTable.edit(entityString);
        }
    }

    public void EditRecord() {
        if (!deviceTypesTable.isEditorActive()) {
            entityString = deviceTypesTable.getSingleSelected();
            if (entityString != null) {
                deviceTypesTable.edit(entityString);
            }
        }
    }

    @Subscribe("deviceTypesTable.create")
    public void onDeviceTypesTableCreate(Action.ActionPerformedEvent event) {
        CreateRecord();
    }

    @Subscribe("deviceTypesTable.edit")
    public void onDeviceTypesTableEdit(Action.ActionPerformedEvent event) {
        EditRecord();
    }

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorClose(DataGrid.EditorCloseEvent event) {
       DeviceType selected = deviceTypesTable.getEditedItem();
        if (selected ==  null )
        {
            dataContext.remove(entityString);
        }
        getScreenData().loadAll();
    }


}