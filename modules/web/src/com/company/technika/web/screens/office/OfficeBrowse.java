package com.company.technika.web.screens.office;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Office;

import javax.inject.Inject;

@UiController("technika_Office.browse")
@UiDescriptor("office-browse.xml")
@LookupComponent("officesTable")
@LoadDataBeforeShow
public class OfficeBrowse extends StandardLookup<Office> {


    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Office> officesTable;
    @Inject
    private CollectionContainer<Office> officesDc;
    @Inject
    private Notifications notifications;

    private Office currentEntity = null;

    @Subscribe("officesTable.create")
    public void onOfficesTableCreate(Action.ActionPerformedEvent event) {
        if (!officesTable.isEditorActive()) {
            currentEntity = dataContext.create(Office.class);
            officesDc.getMutableItems().add(currentEntity);
            officesTable.edit(currentEntity);
        }
    }

    @Subscribe("officesTable.edit")
    public void onOfficesTableEdit(Action.ActionPerformedEvent event) {
        currentEntity = officesTable.getSingleSelected();
        if (!officesTable.isEditorActive()) {
            if (currentEntity != null) {
                officesTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("officesTable")
    public void onOfficesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ officesTable.getEditedItem().getName();
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

    @Subscribe("officesTable")
    public void onOfficesTableEditorClose(DataGrid.EditorCloseEvent event) {
        Office selected = officesTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

}