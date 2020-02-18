package com.company.technika.web.screens.post;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Post;

import javax.inject.Inject;

@UiController("technika_Post.browse")
@UiDescriptor("post-browse.xml")
@LookupComponent("postsTable")
@LoadDataBeforeShow
public class PostBrowse extends StandardLookup<Post> {
    @Inject
    private DataGrid<Post> postsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionContainer<Post> postsDc;


    @Subscribe("postsTable.create")
    public void onPostsTableCreate(Action.ActionPerformedEvent event) {
        if (postsTable.isEditorActive()) return;
        Post entity = dataContext.create(Post.class);
        postsDc.getMutableItems().add(entity);
        postsTable.edit(entity);
    }

    @Subscribe("postsTable.edit")
    public void onPostsTableEdit(Action.ActionPerformedEvent event) {
        Post selected = postsTable.getSingleSelected();
        if (selected != null) {
            postsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption("Item is not selected")
                    .show();
        }

    }

    @Subscribe("postsTable")
    public void onPostsTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {

        if (getScreenData().getDataContext().isModified(event.getItem()))
        {
            notifications.create().withCaption("Commit"+ event.getItem().toString()).show();
            getScreenData().getDataContext().commit();
            getScreenData().loadAll();
        }


    }

    @Subscribe("postsTable")
    public void onPostsTableEditorClose(DataGrid.EditorCloseEvent event) {
        if (getScreenData().getDataContext().isModified(event.getItem())) {
            notifications.create().withCaption("Changed" + event.getItem().toString()).show();
            getScreenData().loadAll();
        }

    }

    @Subscribe("postsTable")
    public void onPostsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        
    }
    
    

}