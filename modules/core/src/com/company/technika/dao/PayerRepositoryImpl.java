package com.company.technika.dao;

import com.company.technika.entity.Payer;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PayerRepositoryImpl implements PayerRepository {
    private final Persistence persistence;

    @Autowired
    public PayerRepositoryImpl(Persistence persistence) {
        this.persistence = persistence;
    }


    @Override
    @Transactional
    public void save(Payer payer) {
        EntityManager entityManager= persistence.getEntityManager();
        entityManager.persist(payer);
    }
}
