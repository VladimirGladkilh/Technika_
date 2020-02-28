package com.company.technika.web.screens.office;

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
import com.company.technika.entity.Office;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Office.browse")
@UiDescriptor("office-browse.xml")
@LookupComponent("officesTable")
@LoadDataBeforeShow
public class OfficeBrowse extends StandardLookup<Office> {


    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Office> officesTable;
    @Inject
    private CollectionContainer<Office> officesDc;
    @Inject
    private Notifications notifications;

    private Office currentEntity = null;

    @Subscribe("officesTable.create")
    public void onOfficesTableCreate(Action.ActionPerformedEvent event) {
        if (!officesTable.isEditorActive()) {
            currentEntity = dataContext.create(Office.class);
            officesDc.getMutableItems().add(currentEntity);
            officesTable.edit(currentEntity);
        }
    }

    @Subscribe("officesTable.edit")
    public void onOfficesTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = officesTable.getSingleSelected();
        if (!officesTable.isEditorActive()) {
            if (currentEntity != null) {
                officesTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("officesTable")
    public void onOfficesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Subscribe("officesTable")
    public void onOfficesTableEditorClose(DataGrid.EditorCloseEvent event) {
        Office selected = officesTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Inject
    private FileUploadField upload;
    @Inject
    private CollectionLoader<Office> officesDl;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private DataManager dataManager;
    @Inject
    private EntityImportService entityImportService;

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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Office");
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
        officesDl.load();
    }

}