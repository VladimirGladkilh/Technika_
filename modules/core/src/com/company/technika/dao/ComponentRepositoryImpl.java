package com.company.technika.dao;

import com.company.technika.entity.Component;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ComponentRepositoryImpl implements ComponentRepository {
    private final Persistence persistence;
    @Autowired
    private ComponentRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }
    @Override
    @Transactional
    public void save(Component component) {
        EntityManager entityManager = persistence.getEntityManager();
        entityManager.persist(component);
    }
}
