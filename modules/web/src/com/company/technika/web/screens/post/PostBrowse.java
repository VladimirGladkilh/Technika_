package com.company.technika.web.screens.post;

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
import com.company.technika.entity.Post;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.*;
import java.util.UUID;
import java.util.function.Supplier;

@UiController("technika_Post.browse")
@UiDescriptor("post-browse.xml")
@LookupComponent("postsTable")
@LoadDataBeforeShow
public class PostBrowse extends StandardLookup<Post> {
    @Inject
    private DataGrid<Post> postsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionContainer<Post> postsDc;

    @Inject
    private FileUploadField upload;
    private Post currentEntity = null;
    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    private DataManager dataManager;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private CollectionLoader<Post> postsDl;
    @Inject
    private EntityImportService entityImportService;

    @Subscribe("postsTable.create")
    public void onPostsTableCreate(Action.ActionPerformedEvent event) {
        if (!postsTable.isEditorActive()) {
            currentEntity = dataContext.create(Post.class);
            postsDc.getMutableItems().add(currentEntity);
            postsTable.edit(currentEntity);
        }
    }

    @Subscribe("postsTable.edit")
    public void onPostsTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = postsTable.getSingleSelected();
        if (!postsTable.isEditorActive()) {
            if (currentEntity != null) {
                postsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("postsTable")
    public void onPostsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }



    @Subscribe("postsTable")
    public void onPostsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Post selected = postsTable.getEditedItem();
        if (selected == null) {
            if (currentEntity != null) {
                dataContext.remove(currentEntity);
            }
        }
        getScreenData().loadAll();
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
            String importResult = entityImportService.entityImportFromFile(fileDescriptor, "Post");
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

        postsDl.load();
    }

    @Subscribe("my")
    public void onMyClick(Button.ClickEvent event) {
        String postName="програмист";
        String postQuery="select p from technika_Post p where lower(trim(p.name))=:name and p.deleteTs is null";
        LoadContext<Post> lcp = LoadContext.create(Post.class)
                .setQuery(LoadContext.createQuery(postQuery)
                        .setParameter("name", postName)
                        .setMaxResults(1));
        Post post = dataManager.load(lcp);
    }


}