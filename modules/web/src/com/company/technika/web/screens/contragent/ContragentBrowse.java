package com.company.technika.web.screens.contragent;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Contragent;

import javax.inject.Inject;

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
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Subscribe("contragentsTable")
    public void onContragentsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ contragentsTable.getEditedItem().getName();
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