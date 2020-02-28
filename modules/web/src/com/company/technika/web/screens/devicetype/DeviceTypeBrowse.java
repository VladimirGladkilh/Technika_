package com.company.technika.web.screens.devicetype;

import com.company.technika.service.EntityImportService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;

import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.DeviceType;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;


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
        getScreenData().getDataContext().commit();
    }

    @Subscribe("deviceTypesTable")
    public void onDeviceTypesTableEditorClose(DataGrid.EditorCloseEvent event) {
        DeviceType selected = deviceTypesTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
    }

    @Inject
    private EntityImportService entityImportService;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileUploadField upload;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<DeviceType> deviceTypesDl;

    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        UUID fileUUID = upload.getFileId();
        String fileName = upload.getFileName();
        FileDescriptor fileDescriptor = fileUploadingAPI.getFileDescriptor(fileUUID, fileName);
        File file = fileUploadingAPI.getFile(fileUUID);
        Supplier<InputStream> inputStreamSupplier = () -> {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new FileLoader.InputStreamSupplierException("File is not found " + file.getAbsolutePath());
            }
        };
        try {
            fileLoader.saveStream(fileDescriptor, inputStreamSupplier);
        }
        catch (FileStorageException e) {
            throw new RuntimeException(e);
        }
        dataManager.commit(fileDescriptor);
        try {
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "DeviceType");
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Результат импорта")
                    .withDescription(importResult)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String error = entityImportService.entityErrorMessage();
        if (!error.equals("")){
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Ошибка импорта")
                    .withDescription(error)
                    .show();
        }
        deviceTypesDl.load();
    }


}