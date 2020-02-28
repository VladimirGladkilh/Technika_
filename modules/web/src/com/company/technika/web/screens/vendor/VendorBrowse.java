package com.company.technika.web.screens.vendor;

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
import com.company.technika.entity.Vendor;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Vendor.browse")
@UiDescriptor("vendor-browse.xml")
@LookupComponent("vendorsTable")
@LoadDataBeforeShow
public class VendorBrowse extends StandardLookup<Vendor> {

    @Inject
    private EntityImportService entityImportService;
    @Inject
    private DataGrid<Vendor> vendorsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionContainer<Vendor> vendorsDc;
    @Inject
    private Notifications notifications;

    private Vendor currentEntity= null;
    @Inject
    private FileUploadField upload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private DataManager dataManager;

    @Inject
    private CollectionLoader<Vendor> vendorsDl;

    @Subscribe("vendorsTable.create")
    public void onVendorsTableCreate(Action.ActionPerformedEvent event) {
        if (!vendorsTable.isEditorActive()) {
            currentEntity = dataContext.create(Vendor.class);
            vendorsDc.getMutableItems().add(currentEntity);
            vendorsTable.edit(currentEntity);
        }
    }

    @Subscribe("vendorsTable.edit")
    public void onVendorsTableEdit(Action.ActionPerformedEvent event) {
        if (!vendorsTable.isEditorActive()) {
            if (currentEntity != null) {
                vendorsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("vendorsTable")
    public void onVendorsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Vendor selected = vendorsTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
    }

    @Subscribe("vendorsTable")
    public void onVendorsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Vendor");
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

        vendorsDl.load();
    }
}