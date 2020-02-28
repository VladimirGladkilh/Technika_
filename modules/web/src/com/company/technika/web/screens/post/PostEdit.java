package com.company.technika.web.screens.post;

import com.haulmont.cuba.gui.screen.*;
import com.company.technika.entity.Post;

@UiController("technika_Post.edit")
@UiDescriptor("post-edit.xml")
@EditedEntityContainer("postDc")
@LoadDataBeforeShow
public class PostEdit extends StandardEditor<Post> {
}