package com.company.technika.web.screens.component;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Component;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.io.Console;

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
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }


}