package com.company.technika.web.screens.payer;

import com.company.technika.service.EntityImportService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Payer;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Payer.browse")
@UiDescriptor("payer-browse.xml")
@LookupComponent("payersTable")
@LoadDataBeforeShow
public class PayerBrowse extends StandardLookup<Payer> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Payer> payersTable;
    @Inject
    private CollectionContainer<Payer> payersDc;

    private Payer currentEntity = null;

    @Subscribe("payersTable.create")
    public void onPayersTableCreate(Action.ActionPerformedEvent event) {
        if (!payersTable.isEditorActive()){
            currentEntity = dataContext.create(Payer.class);
            payersDc.getMutableItems().add(currentEntity);
            payersTable.edit(currentEntity);
        }
    }

    @Subscribe("payersTable.edit")
    public void onPayersTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = payersTable.getSingleSelected();
        if (!payersTable.isEditorActive()){
            if (currentEntity != null) {
                payersTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("payersTable")
    public void onPayersTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Subscribe("payersTable")
    public void onPayersTableEditorClose(DataGrid.EditorCloseEvent event) {
        Payer selected = payersTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
    }

    @Inject
    private FileUploadField upload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Payer> payersDl;
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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Payer");
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

        payersDl.load();

    }
}