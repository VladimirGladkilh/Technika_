package com.company.technika.web.screens.person;

import com.company.technika.entity.Post;
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
import com.company.technika.entity.Person;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Person.browse")
@UiDescriptor("person-browse.xml")
@LookupComponent("personsTable")
@LoadDataBeforeShow
public class PersonBrowse extends StandardLookup<Person> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Person> personsTable;
    @Inject
    private CollectionContainer<Person> personsDc;

    private Person currentEntity = null;

    @Subscribe("personsTable.create")
    public void onPersonsTableCreate(Action.ActionPerformedEvent event) {
        if (!personsTable.isEditorActive()){
            currentEntity = dataContext.create(Person.class);
            personsDc.getMutableItems().add(currentEntity);
            personsTable.edit(currentEntity);
        }
    }

    @Subscribe("personsTable.edit")
    public void onPersonsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = personsTable.getSingleSelected();
        if (!personsTable.isEditorActive()){
            if (currentEntity != null) {
                personsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("personsTable")
    public void onPersonsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Person selected = personsTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
    }

    @Subscribe("personsTable")
    public void onPersonsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
            getScreenData().getDataContext().commit();
    }

    @Inject
    private EntityImportService entityImportService;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private CollectionLoader<Person> personsDl;
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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Person");
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
        personsDl.load();
    }
}