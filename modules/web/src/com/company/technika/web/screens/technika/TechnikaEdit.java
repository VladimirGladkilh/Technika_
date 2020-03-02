package com.company.technika.web.screens.technika;

import com.company.technika.entity.*;
import com.haulmont.cuba.core.global.RemoteException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("technika_Techika.edit")
@UiDescriptor("technika-edit.xml")
@EditedEntityContainer("technikaDc")
@LoadDataBeforeShow
public class TechnikaEdit extends StandardEditor<Technika> {
    @Inject
    private DataGrid<Movement> movementDataGrid;
    @Inject
    private DataGrid<Equipment> equipmentDataGrid;
    @Inject
    private InstanceContainer<Technika> technikaDc;
    @Inject
    private CollectionPropertyContainer<Movement> movementDc;
    @Inject
    private CollectionPropertyContainer<Equipment> equipmentDc;
    @Inject
    private DataContext dataContext;
    private Equipment currentEquipment;
    private Technika currentEntity;
    private Movement currentMovement;

    @Subscribe("equipmentDataGrid")
    public void onEquipmentDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        Equipment selectedEquipment = equipmentDataGrid.getEditedItem();
        if (selectedEquipment == null && currentEquipment != null){
            dataContext.remove(currentEquipment);
        }
        getScreenData().loadAll();
    }

    @Subscribe("equipmentDataGrid")
    public void onEquipmentDataGridEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        Equipment editedItem = equipmentDataGrid.getEditedItem();
        if (editedItem != null) {
            if (getEditedEntity().getName()== null) {
                Component component = equipmentDataGrid.getItems().getItemByIndex(0).getComponent();//
                if (component != null) {
                    Device device = component.getDevice();
                    if (device != null) {
                        getEditedEntity().setName(device.getName() + " " + component.getSerialNumber());
                    }
                }
            }
        }
    }
    @Subscribe("equipmentDataGrid")
    public void onEquipmentDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {

        getScreenData().getDataContext().commit();
    }

    @Subscribe("equipmentDataGrid.create")
    public void onEquipmentDataGridCreate(Action.ActionPerformedEvent event) {
        if (!equipmentDataGrid.isEditorActive()){
            currentEquipment = dataContext.create(Equipment.class);
            currentEquipment.setTechnika(getEditedEntity());
            equipmentDc.getMutableItems().add(currentEquipment);
            equipmentDataGrid.edit(currentEquipment);
        }
    }

    @Subscribe("equipmentDataGrid.edit")
    public void onEquipmentDataGridEdit(Action.ActionPerformedEvent event) {
        currentEquipment = equipmentDataGrid.getSingleSelected();
        if (!equipmentDataGrid.isEditorActive() && currentEquipment!= null){
            equipmentDataGrid.edit(currentEquipment);
        }
    }

    @Subscribe("movementDataGrid")
    public void onMovementDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        Movement selectedMovement = movementDataGrid.getEditedItem();
        if (selectedMovement == null && currentMovement != null){
            dataContext.remove(selectedMovement);
        }
        getScreenData().loadAll();
    }

    @Subscribe("movementDataGrid")
    public void onMovementDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
    }

    @Subscribe("movementDataGrid.create")
    public void onMovementDataGridCreate(Action.ActionPerformedEvent event) {
        if (!movementDataGrid.isEditorActive()){
            currentMovement = dataContext.create(Movement.class);
            currentMovement.setTechnika(getEditedEntity());
            movementDc.getMutableItems().add(currentMovement);
            movementDataGrid.edit(currentMovement);
        }
    }

    @Subscribe("movementDataGrid.edit")
    public void onMovementDataGridEdit(Action.ActionPerformedEvent event) {
        currentMovement = movementDataGrid.getSingleSelected();
        if (!movementDataGrid.isEditorActive() && currentMovement!= null){
            movementDataGrid.edit(currentMovement);
        }
    }



    @Subscribe
    public void onInitEntity(InitEntityEvent<Technika> event) {
        currentEntity = getEditedEntity();
    }

    @Subscribe
    public void onInit(InitEvent event) {
        currentEntity = getEditedEntity();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        if (getEditedEntity().getName()== null && equipmentDataGrid.getItems().size()>0) {
            Component component = equipmentDataGrid.getItems().getItemByIndex(0).getComponent();//
            if (component != null) {
                Device device = component.getDevice();
                if (device != null) {
                    String dName = device.getName();
                    getEditedEntity().setName(dName + " "+ component.getSerialNumber());
                }
            }
        }
    }



}