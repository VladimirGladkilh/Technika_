package com.company.technika.web.screens.technika;

import com.company.technika.entity.Equipment;
import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Technika;

import javax.inject.Inject;

@UiController("technika_Techika.edit")
@UiDescriptor("technika-edit.xml")
@EditedEntityContainer("technikaDc")
@LoadDataBeforeShow
public class TechnikaEdit extends StandardEditor<Technika> {
    @Inject
    private Notifications notifications;
    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Equipment> dataGrid;
    @Inject
    private CollectionPropertyContainer<Equipment> equipmentDc;

    private Equipment currentEntity = null;

    @Subscribe("dataGrid.create")
    public void onDataGridCreate(Action.ActionPerformedEvent event) {
        if (!dataGrid.isEditorActive()){
            currentEntity = dataContext.create(Equipment.class);
            currentEntity.setTechnika(getEditedEntity());
            equipmentDc.getMutableItems().add(currentEntity);
            dataGrid.edit(currentEntity);
        }
    }

    @Subscribe("dataGrid.edit")
    public void onDataGridEdit(Action.ActionPerformedEvent event) {
        currentEntity = dataGrid.getSingleSelected();
        if (!dataGrid.isEditorActive()){
            if (currentEntity != null) {
                dataGrid.edit(currentEntity);
            }
        }
    }

    @Subscribe("dataGrid")
    public void onDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        Equipment selected = dataGrid.getEditedItem();
        if (selected == null && currentEntity != null) {
            dataContext.remove(currentEntity);
        }
        getScreenData().loadAll();
    }

    @Subscribe("dataGrid")
    public void onDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        String message = "";
        String val = "Скорректируйте значение "+ dataGrid.getEditedItem().getComponent().toString();
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