package com.company.technika.web.screens.cost;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Cost;

import javax.inject.Inject;

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