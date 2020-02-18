package com.company.technika.web.screens.devicetype;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
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

    @Inject
    private Notifications notifications;


    private DeviceType currentEntity = null;


    @Subscribe("deviceTypesTable.create")
    public void onDeviceTypesTableCreate(Action.ActionPerformedEvent event) {
        if (!deviceTypesTable.isEditorActive())
        {
            currentEntity = dataContext.create(DeviceType.class);
            deviceTypesDc.getMutableItems().add(currentEntity); //добавляет пустую сущность
            deviceTypesTable.edit(currentEntity);
        }
    }

    @Subscribe("deviceTypesTable.edit")
    public void onDeviceTypesTableEdit(Action.ActionPerformedEvent event) {
        if (!deviceTypesTable.isEditorActive()) {
            currentEntity = deviceTypesTable.getSingleSelected();
            if (currentEntity != null) {
                deviceTypesTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ deviceTypesTable.getEditedItem().getName();
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

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorClose(DataGrid.EditorCloseEvent event) {
        DeviceType selected = deviceTypesTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }


}