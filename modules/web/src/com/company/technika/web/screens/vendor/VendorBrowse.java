package com.company.technika.web.screens.vendor;

import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Vendor;

import javax.inject.Inject;

@UiController("technika_Vendor.browse")
@UiDescriptor("vendor-browse.xml")
@LookupComponent("vendorsTable")
@LoadDataBeforeShow
public class VendorBrowse extends StandardLookup<Vendor> {



    @Inject
    private DataGrid<Vendor> vendorsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionContainer<Vendor> vendorsDc;
    @Inject
    private Notifications notifications;

    private Vendor currentEntity= null;

    @Subscribe("vendorsTable.create")
    public void onVendorsTableCreate(Action.ActionPerformedEvent event) {
        if (!vendorsTable.isEditorActive()) {
            currentEntity = dataContext.create(Vendor.class);
            vendorsDc.getMutableItems().add(currentEntity);
            vendorsTable.edit(currentEntity);
        }
    }

    @Subscribe("vendorsTable.edit")
    public void onVendorsTableEdit(Action.ActionPerformedEvent event) {
        if (!vendorsTable.isEditorActive()) {
            if (currentEntity != null) {
                vendorsTable.edit(currentEntity);
            }
        }
    }

    @Subscribe("vendorsTable")
    public void onVendorsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Vendor selected = vendorsTable.getEditedItem();
        if (selected == null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Subscribe("vendorsTable")
    public void onVendorsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ vendorsTable.getEditedItem().getName();
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