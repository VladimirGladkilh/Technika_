package com.company.technika.web.screens.device;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Device;

import javax.inject.Inject;

@UiController("technika_Device.browse")
@UiDescriptor("device-browse.xml")
@LookupComponent("devicesTable")
@LoadDataBeforeShow
public class DeviceBrowse extends StandardLookup<Device> {

    @Inject
    private CollectionContainer<Device> devicesDc;
    @Inject
    private DataGrid<Device> devicesTable;
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;

    @Subscribe("devicesTable.create")
    public void onDevicesTableCreate(Action.ActionPerformedEvent event) {
        if (devicesTable.isEditorActive()) return;
        Device entity = dataContext.create(Device.class);
        devicesDc.getMutableItems().add(entity);
        devicesTable.edit(entity);
    }

    @Subscribe("devicesTable.edit")
    public void onPostsTableEdit(Action.ActionPerformedEvent event) {
        Device selected = devicesTable.getSingleSelected();
        if (selected != null) {
            devicesTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption("Item is not selected")
                    .show();
        }
    }

    @Subscribe("devicesTable")
    public void onDevicesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Subscribe("devicesTable")
    public void onDevicesTableEditorClose(DataGrid.EditorCloseEvent event) {
        getScreenData().loadAll();
    }
}