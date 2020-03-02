package com.company.technika.web.screens.device;

import com.company.technika.service.EntityImportService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Device;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

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
        getScreenData().getDataContext().commit();
    }

    @Subscribe("devicesTable")
    public void onDevicesTableEditorClose(DataGrid.EditorCloseEvent event) {
        Device selected = devicesTable.getEditedItem();
        if (selected == null && currentEntity !=null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Inject
    private EntityImportService entityImportService;
    @Inject
    private FileUploadField upload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Device> devicesDl;

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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Device");
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
        devicesDl.load();
    }
}