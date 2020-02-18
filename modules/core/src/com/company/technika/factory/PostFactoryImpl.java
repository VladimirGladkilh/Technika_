package com.company.technika.factory;

import com.company.technika.entity.Post;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;

public class PostFactoryImpl implements PostFactory {

    private final Metadata metadata;
    @Autowired
    public PostFactoryImpl(Metadata metadata) {
        this.metadata = metadata;
    }
    @Override
    public Post create(String name){
        Post post = metadata.create(Post.class);
        post.setName(name);
        return post;
    }
}
