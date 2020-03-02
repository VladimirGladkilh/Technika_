package com.company.technika.web.screens.component;

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
import com.company.technika.entity.Component;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Component.browse")
@UiDescriptor("component-browse.xml")
@LookupComponent("componentsTable")
@LoadDataBeforeShow
public class ComponentBrowse extends StandardLookup<Component> {

    @Inject
    private DataGrid<Component> componentsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionContainer<Component> componentsDc;
    @Inject
    private Notifications notifications;

    private Component currentEntity = null;

    @Subscribe("componentsTable.create")
    public void onComponentsTableCreate(Action.ActionPerformedEvent event) {
        if (!componentsTable.isEditorActive()){
            currentEntity = dataContext.create(Component.class);
            componentsDc.getMutableItems().add(currentEntity);
            componentsTable.edit(currentEntity);
        }
    }

    @Subscribe("componentsTable.edit")
    public void onComponentsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = componentsTable.getSingleSelected();
        if (!componentsTable.isEditorActive()){
            if (currentEntity != null) {
                componentsTable.edit(currentEntity);
            }
        }
    }
    @Subscribe("componentsTable")
    public void onComponentsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ componentsTable.getEditedItem().getDevice().getModel();
        try {
            getScreenData().getDataContext().commit();
        }
        catch (EntityExistsException ex){
            message = "Сущность уже существует. " + ex.getLocalizedMessage();
        }
        catch (NonUniqueResultException ex){
            message = "Значение уже существует. " +ex.getLocalizedMessage();
        }
        catch (TransactionRequiredException ex){
            message = "Ошибка транзакции. " +ex.getLocalizedMessage();
        }
        catch (RollbackException ex){
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

    @Subscribe("componentsTable")
    public void onComponentsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Component selected = componentsTable.getEditedItem();
        if (selected == null && currentEntity!=null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Inject
    private CollectionLoader<Component> componentsDl;
    @Inject
    private EntityImportService entityImportService;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private FileUploadField upload;
    @Inject
    private DataManager dataManager;

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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Component");
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
        componentsDl.load();
    }


}