package com.company.technika.dao;

import com.company.technika.entity.Post;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository //
public class PostRepositoryImpl implements PostRepository {
    private final Persistence persistence;

    @Autowired
    public PostRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }


    @Override
    @Transactional
    public void save(Post post) {
        EntityManager entityManager = persistence.getEntityManager();
        entityManager.persist(post);
    }
}//
