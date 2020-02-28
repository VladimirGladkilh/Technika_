package com.company.technika.web.screens.contragent;

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
import com.company.technika.entity.Contragent;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Contragent.browse")
@UiDescriptor("contragent-browse.xml")
@LookupComponent("contragentsTable")
@LoadDataBeforeShow
public class ContragentBrowse extends StandardLookup<Contragent> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Contragent> contragentsTable;
    @Inject
    private CollectionContainer<Contragent> contragentsDc;
    
    private Contragent currentEntity = null;

    @Subscribe("contragentsTable.create")
    public void onContragentsTableCreate(Action.ActionPerformedEvent event) {
        if (!contragentsTable.isEditorActive()){
            currentEntity = dataContext.create(Contragent.class);
            contragentsDc.getMutableItems().add(currentEntity);
            contragentsTable.edit(currentEntity);
        }
    }

    @Subscribe("contragentsTable.edit")
    public void onContragentsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = contragentsTable.getSingleSelected();
        if (!contragentsTable.isEditorActive()){
            if (currentEntity != null) {
                contragentsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("contragentsTable")
    public void onContragentsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Contragent selected = contragentsTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
    }

    @Subscribe("contragentsTable")
    public void onContragentsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<Contragent> contragentsDl;
    @Inject
    private EntityImportService entityImportService;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileUploadField upload;

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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Contragent");
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
        contragentsDl.load();
    }
    
    
}