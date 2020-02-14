package com.company.technika.web.screens.component;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Component;

import javax.inject.Inject;
import java.io.Console;

@UiController("technika_Component.browse")
@UiDescriptor("component-browse.xml")
@LookupComponent("componentsTable")
@LoadDataBeforeShow
public class ComponentBrowse extends StandardLookup<Component> {
    @Subscribe("componentsTable")
    public void onComponentsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();

    }

    @Inject
    private DataGrid<Component> componentsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionContainer<Component> componentsDc;

    @Subscribe("componentsTable.create")
    public void onComponentsTableCreate(Action.ActionPerformedEvent event) {
        Component entity = dataContext.create(Component.class);
        componentsDc.getMutableItems().add(entity);
        componentsTable.edit(entity);
    }
}