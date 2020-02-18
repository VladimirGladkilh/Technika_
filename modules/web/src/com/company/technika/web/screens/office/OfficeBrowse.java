package com.company.technika.web.screens.office;

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
    @Subscribe("officesTable")
    public void onOfficesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();

    }

    @Inject
    private DataContext dataContext;
    @Inject
    private DataGrid<Office> officesTable;
    @Inject
    private CollectionContainer<Office> officesDc;

    @Subscribe("officesTable.create")
    public void onOfficesTableCreate(Action.ActionPerformedEvent event) {
        Office entyty = dataContext.create(Office.class);
        officesDc.getMutableItems().add(entyty);
        officesTable.edit(entyty);
    }
}