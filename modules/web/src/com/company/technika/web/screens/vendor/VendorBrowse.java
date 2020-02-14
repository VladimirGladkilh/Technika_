package com.company.technika.web.screens.vendor;

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

    @Subscribe("vendorsTable.create")
    public void onVendorsTableCreate(Action.ActionPerformedEvent event) {
        Vendor vendor = dataContext.create(Vendor.class);
        vendorsDc.getMutableItems().add(vendor);
        vendorsTable.edit(vendor);

    }

    @Subscribe("vendorsTable")
    public void onVendorsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();
    }
}