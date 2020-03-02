package com.company.technika.dao;

import com.company.technika.entity.Cost;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CostRepositoryImpl implements CostRepository {
    private final Persistence persistence;

    @Autowired
    public CostRepositoryImpl(Persistence persistence){
        this.persistence = persistence;
    }
    @Override
    @Transactional
    public void save(Cost cost) {
        EntityManager entityManager= persistence.getEntityManager();
        entityManager.persist(cost);

    }
}
