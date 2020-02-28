package com.company.technika.factory;

import com.company.technika.entity.Post;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PostFactoryImpl implements PostFactory {
    private final Metadata metadata;

    @Autowired
    private PostFactoryImpl(Metadata metadata) {
        this.metadata = metadata;
    }
    @Override
    public Post create(String postName) {
        Post post = metadata.create(Post.class);
        post.setName(postName);
        return post;
    }
}//
