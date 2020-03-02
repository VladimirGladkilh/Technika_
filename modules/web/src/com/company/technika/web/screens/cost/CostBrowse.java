package com.company.technika.web.screens.cost;

import com.company.technika.service.EntityImportService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Cost;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Cost.browse")
@UiDescriptor("cost-browse.xml")
@LookupComponent("costsTable")
@LoadDataBeforeShow
public class CostBrowse extends StandardLookup<Cost> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Cost> costsTable;
    @Inject
    private CollectionContainer<Cost> costsDc;
    
    private Cost currentEntity = null;
    @Subscribe("costsTable.create")
    public void onCostsTableCreate(Action.ActionPerformedEvent event) {
        if (!costsTable.isEditorActive()){
            currentEntity = dataContext.create(Cost.class);
            costsDc.getMutableItems().add(currentEntity);
            costsTable.edit(currentEntity);
        }
    }

    @Subscribe("costsTable.edit")
    public void onCostsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = costsTable.getSingleSelected();
        if (!costsTable.isEditorActive()){
            if (currentEntity != null) {
                costsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("costsTable")
    public void onCostsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Cost selected = costsTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Subscribe("costsTable")
    public void onCostsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ costsTable.getEditedItem().getSubject();
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

    @Inject
    private CollectionLoader<Cost> costsDl;
    @Inject
    private EntityImportService entityImportService;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Cost");
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
        costsDl.load();
    }

    @Subscribe("my")
    public void onMyClick(Button.ClickEvent event) {
        String costQuery="select p from technika_Cost p where lower(trim(p.num))=:name and p.deleteTs is null";
        LoadContext<Cost> lcp = LoadContext.create(Cost.class)
                .setQuery(LoadContext.createQuery(costQuery)
                        .setParameter("name", "5887")
                        .setMaxResults(1));
        String s= dataManager.load(lcp).getSubject();
        notifications.create().withDescription(s).show();

    }


    
}