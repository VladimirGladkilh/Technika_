package com.company.technika.web.screens.device;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.RemoteException;
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

    private Device currentEntity = null;

    @Subscribe("devicesTable.create")
    public void onDevicesTableCreate(Action.ActionPerformedEvent event) {
        if (!devicesTable.isEditorActive()){
            currentEntity = dataContext.create(Device.class);
            devicesDc.getMutableItems().add(currentEntity);
            devicesTable.edit(currentEntity);
        }
    }

    @Subscribe("devicesTable.edit")
    public void onPostsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = devicesTable.getSingleSelected();
        if (!devicesTable.isEditorActive()){
            if (currentEntity != null) {
                devicesTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("devicesTable")
    public void onDevicesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ devicesTable.getEditedItem().getModel();
        try {
            getScreenData().getDataContext().commit();
        }
        catch (javax.persistence.EntityExistsException ex){
            message = "Сущность уже существует. " + ex.getLocalizedMessage();
        }
        catch (javax.persistence.NonUniqueResultException ex){
            message = "Значение уже существует. " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.TransactionRequiredException ex){
            message = "Ошибка транзакции. " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.RollbackException ex){
            message = "Ошибка отката. " +ex.getLocalizedMessage();

        }
        catch (RemoteException ex){
            message = "Ошибка внутри сервера. " +ex.getLocalizedMessage();
        }
        catch (Exception ex){
            message = "Видимо что то случилось. " +ex.getLocalizedMessage();
        }
        if (!message.equals("")){
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка сохранения")
                    .withDescription(message + '\n'+ val)
                    .show();

        }
    }

    @Subscribe("devicesTable")
    public void onDevicesTableEditorClose(DataGrid.EditorCloseEvent event) {
        Device selected = devicesTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }
}