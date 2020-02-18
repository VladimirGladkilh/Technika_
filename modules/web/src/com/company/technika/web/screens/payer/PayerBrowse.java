package com.company.technika.web.screens.payer;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Payer;

import javax.inject.Inject;

@UiController("technika_Payer.browse")
@UiDescriptor("payer-browse.xml")
@LookupComponent("payersTable")
@LoadDataBeforeShow
public class PayerBrowse extends StandardLookup<Payer> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Payer> payersTable;
    @Inject
    private CollectionContainer<Payer> payersDc;

    private Payer currentEntity = null;

    @Subscribe("payersTable.create")
    public void onPayersTableCreate(Action.ActionPerformedEvent event) {
        if (!payersTable.isEditorActive()){
            currentEntity = dataContext.create(Payer.class);
            payersDc.getMutableItems().add(currentEntity);
            payersTable.edit(currentEntity);
        }
    }

    @Subscribe("payersTable.edit")
    public void onPayersTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = payersTable.getSingleSelected();
        if (!payersTable.isEditorActive()){
            if (currentEntity != null) {
                payersTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("payersTable")
    public void onPayersTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ payersTable.getEditedItem().getName();
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

    @Subscribe("payersTable")
    public void onPayersTableEditorClose(DataGrid.EditorCloseEvent event) {
        Payer selected = payersTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }
}