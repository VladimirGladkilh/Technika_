package com.company.technika.web.screens.post;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Post;

import javax.inject.Inject;

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

    private Post currentEntity = null;

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
        String message = "";
        String val = "Скорректируйте значение: "+ postsTable.getEditedItem().getName();
        try {
            getScreenData().getDataContext().commit();
        }
        catch (javax.persistence.EntityExistsException ex){
            message = "Сущность уже существует: " + ex.getLocalizedMessage();
        }
        catch (javax.persistence.NonUniqueResultException ex){
            message = "Значение уже существует: " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.TransactionRequiredException ex){
            message = "Ошибка транзакции: " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.RollbackException ex){
            message = "Ошибка отката: " +ex.getLocalizedMessage();

        }
        catch (RemoteException ex){
            message = "Ошибка внутри сервера: " +ex.getLocalizedMessage();
        }
        catch (Exception ex){
            message = "Видимо что то случилось: " +ex.getLocalizedMessage();
        }
        if (!message.equals("")){
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Ошибка сохранения")
                    .withDescription(message + '\n'+ val)
                    .show();

        }

    }

    @Subscribe("postsTable")
    public void onPostsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Post selected = postsTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

}