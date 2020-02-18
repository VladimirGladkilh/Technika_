package com.company.technika.web.screens.person;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Person;

import javax.inject.Inject;

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
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Subscribe("personsTable")
    public void onPersonsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ personsTable.getEditedItem().getFamilia()+" " + personsTable.getEditedItem().getImya();
        try {
            getScreenData().getDataContext().commit();
        }
        catch (javax.persistence.EntityExistsException ex){
            message = "Сущность уже существует. " + ex.getLocalizedMessage();
        }
        catch (javax.persistence.NonUniqueResultException ex){
            message = "Значение уже существует. " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.TransactionRequiredException ex){
            message = "Ошибка транзакции. " +ex.getLocalizedMessage();
        }
        catch (javax.persistence.RollbackException ex){
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
    
    
}